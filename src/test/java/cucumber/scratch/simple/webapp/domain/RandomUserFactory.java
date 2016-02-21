package cucumber.scratch.simple.webapp.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scratch.simple.webapp.data.UserRepository;

import static shiver.me.timbers.data.random.RandomStrings.buildSomeString;

@Component
public class RandomUserFactory implements UserFactory {

    private final UserRepository userRepository;

    @Autowired
    public RandomUserFactory(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createNew() {
        return new User(
            buildSomeString().thatContainsAlphanumericCharacters().withLengthBetween(1, 10).build(),
            buildSomeString().thatContainsAlphanumericCharacters().withLengthBetween(1, 10).build()
        );
    }

    @Override
    public User createExisting() {
        final scratch.simple.webapp.domain.User user = userRepository.save(new scratch.simple.webapp.domain.User(
            buildSomeString().thatContainsAlphanumericCharacters().withLengthBetween(1, 10).build(),
            buildSomeString().thatContainsAlphanumericCharacters().withLengthBetween(1, 10).build()
        ));

        return new User(user.getUsername(), user.getPassword());
    }
}
