package vetweb.store.api.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vetweb.store.api.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
