package vetweb.store.api.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vetweb.store.api.models.auth.User;
import vetweb.store.api.persistence.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User findByName(String name) {
		return userRepository.findByName(name);
	}
	
}
