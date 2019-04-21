package vetweb.store.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vetweb.store.api.models.Category;
import vetweb.store.api.models.Product;
import vetweb.store.api.persistence.CategoryRepository;
import vetweb.store.api.persistence.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public Long saveProduct(Product product) {
		return this.productRepository.save(product).getId();
	}
	
	public Long saveCategory(Category category) {
		return this.categoryRepository.save(category).getId();
	}
	
	public List<Product> getProducts() {
		return this.productRepository.findAll();
	}
	
	public List<Category> getCategories() {
		return this.categoryRepository.findAll();
	}
	
	public Product findById(Long id) {
		return this.productRepository.findById(id).get();
	}
	
	public Category findCategoryById(Long id) {
		return this.categoryRepository.findById(id).get();
	}
	
	public Long edit(Product product) {
		Product prod = this.productRepository.saveAndFlush(product);
		return prod.getId();
	}
	
	public Long editCategory(Category category) {
		Category cat = this.categoryRepository.saveAndFlush(category);
		return cat.getId();
	}
	
	public Product delete(Long id) {
		Product product = this.productRepository.findById(id).get();
		this.productRepository.delete(product);
		return product;
	}
	
	public Category deleteCategory(Long id) {
		Category category = this.categoryRepository.findById(id).get();
		this.categoryRepository.delete(category);
		return category;
	}

}
