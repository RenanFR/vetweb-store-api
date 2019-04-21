package vetweb.store.api.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vetweb.store.api.models.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

}
