package vetweb.store.api.config.security.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import vetweb.store.api.service.auth.UserService;

//Get user information and generate jwt token and the structure
@Service
public class TokenAuthService {
	
	private static final long EXPIRATION_TIME = 86400000;
    private static final String SECRET = "Vetweb_Secret";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    
    @Autowired
    private UserService userService;
    
    public String createToken(String userName, List<String> roles) {
    	Claims claims = Jwts.claims().setSubject(userName);//Map of custom properties to be added on token
    	claims.put("profiles", roles);
    	Date now = new Date();
		String token = Jwts.builder()
    		.setClaims(claims)
    		.setIssuedAt(now)
    		.setExpiration(new Date(now.getTime() + EXPIRATION_TIME))
    		.signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(SECRET.getBytes()))
    		.compact();
    	return token;
    }
    
    public Authentication getAuthentication(String token) {
    	UserDetails userDetails = userService.loadUserByUsername(getUserFromToken(token));
    	return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
    }
    
    public String getUserFromToken(String token) {
    	String userName = Jwts.parser()
    			.setSigningKey(SECRET)
    			.parseClaimsJws(token)
    			.getBody()
    			.getSubject();
    	return userName; 
    }
    
    public String getAuth(HttpServletRequest request) {
    	String token = request.getHeader(HEADER_STRING);
    	if (token != null && token.startsWith(TOKEN_PREFIX))
    		return token.replaceAll("Bearer ", "");
    	return null;
    }
    
    public boolean validateToken(String token) {
    	try {
    		Jws<Claims> claims = Jwts.parser()
    				.setSigningKey(SECRET)
    				.parseClaimsJws(token);
    		if (claims.getBody().getExpiration().before(new Date())) {
    			return false;
    		}
    		return true;
    	} catch (JwtException | IllegalArgumentException exception) {
    		throw new InvalidJwtAuthenticationException
    	}
    }
    
//    public void addJsonWebTokenToUserResponse(HttpServletResponse response, String userName) {
//		String jwt = Jwts.builder()
//    						.setSubject(userName)
//    						.setExpiration(Date.from(LocalDate.now().plus(EXPIRATION_TIME, ChronoUnit.MILLIS).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()))
//    						.signWith(SignatureAlgorithm.HS512, SECRET)
//    						.compact();
//		String token = TOKEN_PREFIX + " " + jwt;
//		response.addHeader(HEADER_STRING, token);
//    }
    
    
	

}
