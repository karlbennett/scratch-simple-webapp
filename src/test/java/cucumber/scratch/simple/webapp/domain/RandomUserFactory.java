package cucumber.scratch.simple.webapp.domain;

import org.springframework.stereotype.Component;

import static shiver.me.timbers.data.random.RandomStrings.buildSomeString;

@Component
public class RandomUserFactory implements UserFactory {
    @Override
    public User createNew() {
        return new User(
            buildSomeString().thatContainsAlphanumericCharacters().withLengthBetween(1, 10).build(),
            buildSomeString().thatContainsAlphanumericCharacters().withLengthBetween(1, 10).build()
        );
    }
}
