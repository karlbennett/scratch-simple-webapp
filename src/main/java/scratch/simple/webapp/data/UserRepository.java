package scratch.simple.webapp.data;

import org.springframework.data.repository.CrudRepository;
import scratch.simple.webapp.domain.User;

/**
 * This is a Spring Data JPA repository. It is dynamically implemented at runtime by Spring Data.
 */
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * This is a Spring Data query method. It is dynamically implemented by Spring Data using the method name as the
     * query.
     */
    User findByUsername(String username);
}
