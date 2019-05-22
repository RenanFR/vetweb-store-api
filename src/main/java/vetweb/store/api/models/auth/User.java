package vetweb.store.api.models.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import vetweb.store.api.models.Category;

//Model for user information
@Entity
@Table(name = "tbl_user")
public class User implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	
	private String password;
	
	@Column(name = "is_social_login")
	private Boolean isSocialLogin;
	
	@OneToMany(mappedBy = "userRegistration")
	private List<Category> categoriesRegistered;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tbl_user_profile",
				joinColumns = @JoinColumn(
          			name = "user_id", referencedColumnName = "id"), 
    			inverseJoinColumns = @JoinColumn(
					name = "profile_id", referencedColumnName = "role"))
	private List<Profile> profiles = new ArrayList<Profile>();

	public List<Profile> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return profiles;
	}

	@Override
	public String getUsername() {
		return name;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public Boolean isSocialLogin() {
		return isSocialLogin;
	}

	public void setSocialLogin(Boolean isSocialLogin) {
		this.isSocialLogin = isSocialLogin;
	}

}
