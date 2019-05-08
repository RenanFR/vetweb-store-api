package vetweb.store.api.service.auth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import vetweb.store.api.models.auth.Profile;
import vetweb.store.api.models.auth.User;
import vetweb.store.api.persistence.auth.ProfileRepository;
import vetweb.store.api.persistence.auth.UserRepository;

@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository
					.findByName(username)
					.orElseThrow(() -> new UsernameNotFoundException("User with name " + username + " not founded"));
	}
	
	public void signUp(User user) {
		List<Profile> allProfile = user.getProfiles();
		for (Profile profile : allProfile) {
			if (profileRepository.findById(profile.getRole()).orElse(null) == null) {
				profileRepository.save(profile);
			}
		}
		this.userRepository.save(user);
	}
	
	public List<User> getAll() {
		return userRepository.findAll();
	}
	
	public List<Profile> getAllProfiles() {
		return profileRepository.findAll();
	}
	
	public void saveProfile(Profile profile) {
		profileRepository.save(profile);
	}
	
	public boolean userExists(String user) {
		return userRepository.findByName(user).isPresent();
	}
	
	public void saveUser(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}
	
}
