package vetweb.store.api.models.auth;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "tbl_profile")
public class Profile implements GrantedAuthority{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String role;
	
//	@ManyToMany(mappedBy = "profiles")
//	@JsonBackReference
//	private List<User> users = new ArrayList<>();

	public String getRole() {
		return role;
	}

	@Override
	public String getAuthority() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
//	public void setUsers(List<User> users) {
//		this.users = users;
//	}
//	
//	public List<User> getUsers() {
//		return users;
//	}

}
