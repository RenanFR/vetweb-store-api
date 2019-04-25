package vetweb.store.api.config.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import vetweb.store.api.models.auth.User;
import vetweb.store.api.service.auth.UserService;

@Component
public class JWTTokenVerifierFilter extends GenericFilterBean{
	
	@Autowired
	private UserService userService;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String name = TokenAuthService.getAuth((HttpServletRequest)request);
		if (userService == null) {
			
		}
		User user = userService.findByName(name);
		Authentication authentication = null;
		if (user != null) {
			authentication = new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword(), user.getAuthorities());
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}

}
