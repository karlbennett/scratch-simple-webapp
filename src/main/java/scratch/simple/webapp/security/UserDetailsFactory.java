package scratch.simple.webapp.security;

import org.springframework.security.core.userdetails.UserDetails;
import scratch.simple.webapp.domain.User;

public interface UserDetailsFactory {

    UserDetails create(User user);
}
