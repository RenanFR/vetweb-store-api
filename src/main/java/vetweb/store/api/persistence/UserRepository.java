package vetweb.store.api.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vetweb.store.api.models.auth.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	public User findByName(String name);

}
