package vetweb.store.api.config.security.jwt;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import vetweb.store.api.models.auth.User;
import vetweb.store.api.service.auth.UserService;

//Get user information and generate jwt token and the structure
@Service
public class TokenAuthService {
	
	private UserService userService;
	
	@Autowired
    public TokenAuthService(UserService userService) {
		this.userService = userService;
	}

	private static final long EXPIRATION_TIME = 86400000;
    private static final String SECRET = "Vetweb_Secret";
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String HEADER_STRING = "Authorization";
    
    public void addJsonWebTokenToUserResponse(HttpServletResponse response, String userName) { 
		String jwt = Jwts.builder()
    						.setSubject(userName)
    						.setExpiration(Date.from(LocalDate.now().plus(EXPIRATION_TIME, ChronoUnit.MILLIS).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()))
    						.signWith(SignatureAlgorithm.HS512, SECRET)
    						.compact();
		String token = TOKEN_PREFIX + " " + jwt;
		response.addHeader(HEADER_STRING, token);
    }
    
    public Authentication getUserFromToken(String token) {
    	String userName = Jwts.parser()
    			.setSigningKey(SECRET)
    			.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
    			.getBody()
    			.getSubject();
    	User credentials = userService.findByName(userName);
    	return userName != null ? new UsernamePasswordAuthenticationToken(credentials.getName(), credentials.getPassword(), credentials.getAuthorities()) : null; 
    }
    
    public Authentication getAuth(HttpServletRequest request) {
    	String token = request.getHeader(HEADER_STRING);
    	if (token != null)
    		return getUserFromToken(token);
    	return null;
    }
	

}
