package vetweb.store.api.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vetweb.store.api.models.Category;
import vetweb.store.api.service.ProductService;

@RestController
@RequestMapping("categories")
public class CategoryResource {
	
	@Autowired
	private ProductService productService;
	
	@PostMapping
	public ResponseEntity<String> save(@RequestBody Category category) {
		Long saved = this.productService.saveCategory(category);
		ResponseEntity<String> response = new ResponseEntity<String>("The new category " + saved + " was saved successfully", HttpStatus.CREATED);
		return response;
	}
	
	@GetMapping
	public ResponseEntity<List<Category>> getCategories() {
		List<Category> categories = productService.getCategories();
		ResponseEntity<List<Category>> response = new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
		return response;
	}
	
	@GetMapping(path = "{id}")
	public ResponseEntity<Category> findById(@PathVariable("id")Long id) {
		Category findResult = this.productService.findCategoryById(id);
		ResponseEntity<Category> response = new ResponseEntity<Category>(findResult, HttpStatus.OK);
		return response;
	}
	
	@PutMapping
	public ResponseEntity<String> edit(@RequestBody Category category) {
		Long edited = this.productService.editCategory(category);
		ResponseEntity<String> response = new ResponseEntity<>("Category " + edited + " update successfully", HttpStatus.OK);
		return response;
	}
	
	@DeleteMapping(path = "{id}")
	public ResponseEntity<String> delete(@PathVariable("id")Long id) {
		Category deleted = this.productService.deleteCategory(id);
		ResponseEntity<String> response = new ResponseEntity<>("Category " + deleted.getId() + " deleted as requested", HttpStatus.NO_CONTENT);
		return response;
	}

}
