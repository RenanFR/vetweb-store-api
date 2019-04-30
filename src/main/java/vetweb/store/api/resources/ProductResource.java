package vetweb.store.api.resources;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import vetweb.store.api.config.security.jwt.TokenAuthService;
import vetweb.store.api.models.Product;
import vetweb.store.api.service.ProductService;

@RestController
@RequestMapping("products")
public class ProductResource {
	
	@Autowired
	private ProductService productService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductResource.class);
	
	@PostMapping
	public ResponseEntity<String> saveProduct(@RequestBody Product product) {
		Long id = this.productService.saveProduct(product);
		return new ResponseEntity<String>("Your new product was included successfully with identifier " + id, HttpStatus.CREATED); 
	}
	
	@GetMapping
	public ResponseEntity<List<Product>> getProducts(HttpServletRequest request) {
		String token = request.getHeader(TokenAuthService.HEADER_STRING);
		LOGGER.info(token);
		List<Product> products = this.productService.getProducts();
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}
	
	@GetMapping(path = "{id}")
	public ResponseEntity<Product> findById(@PathVariable("id")Long id) {
		Product product = this.productService.findById(id);
		ResponseEntity<Product> response = new ResponseEntity<>(product, HttpStatus.OK);
		return response;
	}
	
	@PutMapping
	public ResponseEntity<String> edit(@RequestBody Product product) {
		Long prod = this.productService.edit(product);
		ResponseEntity<String> response = new ResponseEntity<>("The product with identifier" + prod + "was update successfully", HttpStatus.OK);
		return response;
	}
	
	@DeleteMapping(path = "{id}")
	public ResponseEntity<String> delete(@PathVariable("id")Long id) {
		Product deleted = this.productService.delete(id);
		ResponseEntity<String> response = new ResponseEntity<>("The product " + deleted.getId() + " was deleted successfully", HttpStatus.NO_CONTENT);
		return response;
	}
	
	@GetMapping
	@RequestMapping("total")
	public ResponseEntity<Integer> count() {
		int count = this.productService.getProducts().size();
		ResponseEntity<Integer> response = new ResponseEntity<>(count, HttpStatus.OK);
		return response;
	}

}
