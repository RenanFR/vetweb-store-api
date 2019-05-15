package vetweb.store.api.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vetweb.store.api.models.Category;
import vetweb.store.api.models.PriceRange;
import vetweb.store.api.models.Product;
import vetweb.store.api.persistence.CategoryRepository;
import vetweb.store.api.persistence.ProductRepository;
import vetweb.store.api.resources.ProductResource;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductResource.class);
	
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
		List<Category> categories = this.categoryRepository.findAll();
		double totalProducts = this.getProducts().size();
		categories
		.forEach(cat -> {
			int totalProductsOfCategory = cat.getProducts().size();
			cat.setAmountProducts(totalProductsOfCategory);
			double percentageOfTotal = (totalProductsOfCategory/totalProducts) * 100;
			cat.setPercentageOfTotal(String.valueOf(percentageOfTotal) + "%");
		});
		return categories;
	}
	
	public Product findById(Long id) {
		return this.productRepository.findById(id).get();
	}
	
	public Category findCategoryById(Long id) {
		Category category = this.categoryRepository.findById(id).get();
		category.setAmountProducts(category.getProducts().size());
		return category;
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
	
	public Map<Category, Map<PriceRange, Map<String, List<Product>>>> drillDownByCategoryPriceRangeProduct() {
		Map<Category, Map<PriceRange, Map<String, List<Product>>>> grouped = this.productRepository
			.findAll()
			.stream()
			.map(prod -> {
				if (prod.getPriceRange() == null)	prod.setPriceRange(PriceRange.UNKNOWN);
				return prod;
			})
			.collect(Collectors.groupingBy(prod -> prod.getCategory(),
					Collectors.groupingBy(prod -> prod.getPriceRange(),
					Collectors.groupingBy(prod -> prod.getDescription()))));
		return grouped;
	}

}
