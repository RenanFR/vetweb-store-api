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
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import vetweb.store.api.models.auth.User;

//Class for generate the JWT Token based on user credentials
public class JWTLoginAndTokenGeneratorFilter extends AbstractAuthenticationProcessingFilter{

	protected JWTLoginAndTokenGeneratorFilter(String url, AuthenticationManager authenticationManager) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		User userCredentials = new ObjectMapper().readValue(request.getInputStream(), User.class);//Get user and password data from request to User model
		return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(userCredentials.getName(), userCredentials.getPassword(), userCredentials.getAuthorities()));
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
	}

}
