package vetweb.store.api.resources;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vetweb.store.api.models.Category;
import vetweb.store.api.models.PriceRange;
import vetweb.store.api.models.Product;
import vetweb.store.api.service.ProductService;
import vetweb.store.api.service.utils.FileService;

@RestController
@RequestMapping("products")
public class ProductResource {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private FileService fileService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductResource.class);
	
	private <T> T map(String text, Class<T> tClass) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(text, tClass);
		} catch (JsonParseException | JsonMappingException jsonException) {
			LOGGER.info("Error creating Json");
		} catch (IOException ioException) {
			LOGGER.info("Generic input output exception");
		}
		return null;
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = "multipart/form-data")
	public ResponseEntity<String> saveProduct(@RequestParam("product")String productText, @RequestPart("fileImage")MultipartFile fileImage) 
				throws JsonParseException, JsonMappingException, IOException{
		Product product = this.<Product>map(productText, Product.class);
		String addressFile = fileService.storeFile(fileImage);
		product.setFileImage(addressFile);
		Long id = this.productService.saveProduct(product);
		LOGGER.info("Image stored on cloud with address " + addressFile);
		return new ResponseEntity<String>("Your new product was included successfully with identifier " + id, HttpStatus.CREATED); 
	}
	
	@GetMapping
	public ResponseEntity<List<Product>> getProducts(HttpServletRequest request) {
		List<Product> products = this.productService.getProducts();
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}
	
	@GetMapping(path = "{id}")
	public ResponseEntity<Product> findById(@PathVariable("id")Long id) {
		Product product = this.productService.findById(id);
		ResponseEntity<Product> response = new ResponseEntity<>(product, HttpStatus.OK);
		return response;
	}
	
	@GetMapping(path = "drilldown")
	public Map<Category, Map<PriceRange, Map<String, List<Product>>>> drillDownByCategoryPriceRangeProduct() {
		Map<Category, Map<PriceRange, Map<String, List<Product>>>> drillDown = this.productService.drillDownByCategoryPriceRangeProduct();
		return drillDown;
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
