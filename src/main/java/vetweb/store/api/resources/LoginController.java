package vetweb.store.api.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vetweb.store.api.models.auth.User;
import vetweb.store.api.service.auth.UserService;

@RestController
@RequestMapping("login")
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public void signUp(@RequestBody User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encode = passwordEncoder.encode(user.getPassword());
		user.setPassword(encode);
		this.userService.signUp(user);
	}
	

}
