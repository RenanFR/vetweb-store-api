package vetweb.store.api.config.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import vetweb.store.api.models.auth.User;

//Class for generate the JWT Token based on user credentials
public class JWTLoginAndTokenGeneratorFilter extends UsernamePasswordAuthenticationFilter{
	
	private AuthenticationManager authenticationManager;
	
	public JWTLoginAndTokenGeneratorFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			User userCredentials = new ObjectMapper().readValue(request.getInputStream(), User.class);//Get user and password data from request to User model
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userCredentials.getName(), userCredentials.getPassword(), userCredentials.getAuthorities()));
		} catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}
	
	@Override
	public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		TokenAuthService.addJsonWebTokenToUserResponse(response, authResult.getName());
	}

}
