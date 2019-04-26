package vetweb.store.api.resources;

import static org.springframework.http.ResponseEntity.ok;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vetweb.store.api.config.security.jwt.TokenAuthService;
import vetweb.store.api.models.auth.User;
import vetweb.store.api.service.auth.UserService;

@RestController
@RequestMapping("login")
public class LoginController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenAuthService tokenAuthService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("signup")
	public void signUp(@RequestBody User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encode = passwordEncoder.encode(user.getPassword());
		user.setPassword(encode);
		this.userService.signUp(user);
	}
	
	@PostMapping
	public ResponseEntity login(@RequestBody User user) {
		try {
			String name = user.getName();
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(name, user.getPassword()));
			UserDetails loadUser = userService.loadUserByUsername(name);
			String token = tokenAuthService.createToken(name, loadUser.getAuthorities());
			Map<String, String> userInformationMap = new HashMap<String, String>();
			userInformationMap.put("username", loadUser.getUsername());
			userInformationMap.put("token", token);
			return ok(userInformationMap);
		} catch (AuthenticationException authenticationException) {
			throw new BadCredentialsException("Authentication data provided is invalid");
		}
	}
	

}
