package vetweb.store.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vetweb.store.api.models.Product;
import vetweb.store.api.persistence.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	public Long saveProduct(Product product) {
		return this.productRepository.save(product).getId();
	}
	
	public List<Product> getProducts() {
		return this.productRepository.findAll();
	}
	
	public Product findById(Long id) {
		return this.productRepository.findById(id).get();
	}
	
	public Long edit(Product product) {
		Product prod = this.productRepository.saveAndFlush(product);
		return prod.getId();
	}
	
	public Product delete(Long id) {
		Product product = this.productRepository.findById(id).get();
		this.productRepository.delete(product);
		return product;
	}

}
