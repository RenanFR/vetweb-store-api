package vetweb.store.api.config.security.jwt;

import java.util.Collections;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

//Useful class to generate and read token from token parameters
public class TokenService {
	
	private static final long EXPIRATION_TIME = 86400000;
	
	private static final String SECRET = "Vetweb_Secret";
	
	public static final String TOKEN_PREFIX = "Bearer ";
	
	public static final String HEADER_STRING = "Authorization";
	
	public static void addTokenToResponse(HttpServletResponse response, String userName) {
		String jsonWebToken = createToken(userName);
		response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + jsonWebToken);
	}
	
	public static String createToken(String userName) {
		return TOKEN_PREFIX + " " + Jwts.builder()
			.setSubject(userName)
			.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
			.signWith(SignatureAlgorithm.HS512, SECRET)
			.compact();
	}
	
	public static Authentication getTokenFromRequest(HttpServletRequest request) {
		String jwt = request.getHeader(HEADER_STRING);
		if (jwt != null) {
			String userFromToken = Jwts.parser()
					.setSigningKey(SECRET)
					.parseClaimsJws(jwt.replace(TOKEN_PREFIX, ""))
					.getBody()
					.getSubject();
			if (userFromToken != null) {
				return new UsernamePasswordAuthenticationToken(userFromToken, null, Collections.emptyList());
			}
		}
		return null;
	}

}
