package vetweb.store.api.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vetweb.store.api.models.Product;
import vetweb.store.api.service.ProductService;

@RestController
@RequestMapping("products")
public class ProductResource {
	
	@Autowired
	private ProductService productService;
	
	@PostMapping
	public ResponseEntity<String> saveProduct(@RequestBody Product product) {
		Long id = productService.saveProduct(product);
		return new ResponseEntity<String>("Your new product was included successfully with identifier " + id, HttpStatus.OK); 
	}

}
