package vetweb.store.api.config.security.jwt;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class JWTTokenVerifierFilter extends GenericFilterBean{
	
	private TokenAuthService tokenAuthService;
	
	public JWTTokenVerifierFilter(TokenAuthService tokenAuthService) {
		this.tokenAuthService = tokenAuthService;
	}
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)servletRequest;
		String header = request.getHeader(TokenAuthService.HEADER_STRING);
		if (header == null || !header.startsWith(TokenAuthService.TOKEN_PREFIX)) {
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}
		String name = tokenAuthService.getAuth((HttpServletRequest)request);
		Authentication authentication = new UsernamePasswordAuthenticationToken(name, null, new ArrayList<>());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(servletRequest, servletResponse);
	}

}
