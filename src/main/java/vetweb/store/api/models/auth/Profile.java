package vetweb.store.api.models.auth;

import org.springframework.security.core.GrantedAuthority;

public class Profile implements GrantedAuthority{
	
	private static final long serialVersionUID = 1L;
	
	private String role;

	@Override
	public String getAuthority() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}

}
