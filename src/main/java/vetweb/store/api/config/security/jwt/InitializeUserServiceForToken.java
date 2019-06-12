package vetweb.store.api.config.security.jwt;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vetweb.store.api.service.auth.UserService;

@Component
public class InitializeUserServiceForToken {
	
	@Autowired
	private UserService userService;
	
	@PostConstruct
	public void injectOnStatic() {
		TokenService.setUserService(userService);
	}

}
