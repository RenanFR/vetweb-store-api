package vetweb.store.api.models.auth;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "tbl_profile")
public class Profile implements GrantedAuthority{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String role;
	
	@ManyToMany(mappedBy = "profiles")
	@JsonBackReference
	private Set<User> users = new HashSet<>();

	@Override
	public String getAuthority() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	public Set<User> getUsers() {
		return users;
	}

}
