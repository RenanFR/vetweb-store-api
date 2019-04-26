package vetweb.store.api.config.security.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>{
	
	private TokenAuthService tokenAuthService;

	public JwtConfig(TokenAuthService tokenAuthService) {
		this.tokenAuthService = tokenAuthService;
	}
	
	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		JWTTokenVerifierFilter jwtTokenFilter = new JWTTokenVerifierFilter(tokenAuthService);
		httpSecurity.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
	}

}
