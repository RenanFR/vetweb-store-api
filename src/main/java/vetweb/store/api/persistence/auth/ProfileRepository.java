package vetweb.store.api.persistence.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import vetweb.store.api.models.auth.Profile;

public interface ProfileRepository extends JpaRepository<Profile, String>{
}
