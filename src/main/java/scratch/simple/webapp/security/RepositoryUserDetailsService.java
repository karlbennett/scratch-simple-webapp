package scratch.simple.webapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import scratch.simple.webapp.data.UserRepository;
import scratch.simple.webapp.domain.User;

@Component
public class RepositoryUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserDetailsFactory userDetailsFactory;

    @Autowired
    public RepositoryUserDetailsService(UserRepository userRepository, UserDetailsFactory userDetailsFactory) {
        this.userRepository = userRepository;
        this.userDetailsFactory = userDetailsFactory;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepository.findByUsername(username);

        if (user != null) {
            return userDetailsFactory.create(user);
        }

        throw new UsernameNotFoundException("No user found for username: " + username);
    }
}
