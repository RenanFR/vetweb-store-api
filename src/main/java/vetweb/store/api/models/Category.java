package vetweb.store.api.models;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import vetweb.store.api.models.auth.User;

@Entity
@Table(name = "tbl_category")
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String description;
	
	@OneToMany(mappedBy = "category")
	@JsonIgnore
	private List<Product> products;
	
	@Transient
	private int amountProducts;
	
	@Transient
	private String percentageOfTotal;
	
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User userRegistration;
	
	private LocalDate dateRegistration;
	
	public Category() {
	}

	public Category(Long id, String description) {
		this.id = id;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public int getAmountProducts() {
		return amountProducts;
	}

	public void setAmountProducts(int amountProducts) {
		this.amountProducts = amountProducts;
	}
	
	public String getPercentageOfTotal() {
		return percentageOfTotal;
	}

	public void setPercentageOfTotal(String percentageOfTotal) {
		this.percentageOfTotal = percentageOfTotal;
	}

	public User getUserRegistration() {
		return userRegistration;
	}

	public void setUserRegistration(User userRegistration) {
		this.userRegistration = userRegistration;
	}

	public LocalDate getDateRegistration() {
		return dateRegistration;
	}

	public void setDateRegistration(LocalDate dateRegistration) {
		this.dateRegistration = dateRegistration;
	}

	@Override
	public String toString() {
		return description;
	}
	

}
