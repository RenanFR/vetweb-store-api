package vetweb.store.api.service;

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

}
