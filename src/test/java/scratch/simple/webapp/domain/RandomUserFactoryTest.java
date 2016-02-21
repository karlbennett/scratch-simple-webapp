package scratch.simple.webapp.domain;

import cucumber.scratch.simple.webapp.domain.RandomUserFactory;
import cucumber.scratch.simple.webapp.domain.User;
import org.junit.Test;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;

public class RandomUserFactoryTest {

    @Test
    public void Can_create_a_random_new_user() {

        // When
        final User actual = new RandomUserFactory().createNew();

        // Then
        assertThat(actual.getUsername().length(), allOf(greaterThan(0), lessThanOrEqualTo(10)));
        assertThat(actual.getPassword().length(), allOf(greaterThan(0), lessThanOrEqualTo(255)));
    }
}