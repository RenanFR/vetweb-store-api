package vetweb.store.api.config.security.jwt;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

//Get user information and generate jwt token and the structure
@Service
public class TokenAuthService {
	
	private static final long EXPIRATION_TIME = 86400000;
    private static final String SECRET = "Vetweb_Secret";
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String HEADER_STRING = "Authorization";
    
    public static void addJsonWebTokenToUserResponse(HttpServletResponse response, String userName) {
		String jwt = Jwts.builder()
    						.setSubject(userName)
    						.setExpiration(Date.from(LocalDate.now().plus(EXPIRATION_TIME, ChronoUnit.MILLIS).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()))
    						.signWith(SignatureAlgorithm.HS512, SECRET)
    						.compact();
		String token = TOKEN_PREFIX + " " + jwt;
		response.addHeader(HEADER_STRING, token);
    }
    
    public static String getUserFromToken(String token) {
    	String userName = Jwts.parser()
    			.setSigningKey(SECRET)
    			.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
    			.getBody()
    			.getSubject();
    	return userName != null ? userName : null; 
    }
    
    public static String getAuth(HttpServletRequest request) {
    	String token = request.getHeader(HEADER_STRING);
    	if (token != null)
    		return getUserFromToken(token);
    	return null;
    }
	

}
