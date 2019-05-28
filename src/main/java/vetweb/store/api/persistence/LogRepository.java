package vetweb.store.api.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vetweb.store.api.models.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, String> {

}
