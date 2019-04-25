package vetweb.store.api.config.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class JWTTokenVerifierFilter extends GenericFilterBean{
	
	private TokenAuthService tokenAuthService;
	
	public JWTTokenVerifierFilter() {
	}
	
	@Autowired
	public JWTTokenVerifierFilter(TokenAuthService tokenAuthService) {
		this.tokenAuthService = tokenAuthService;
	}
	
	public void initializeFields() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Authentication authentication = tokenAuthService.getAuth((HttpServletRequest)request);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}

}
