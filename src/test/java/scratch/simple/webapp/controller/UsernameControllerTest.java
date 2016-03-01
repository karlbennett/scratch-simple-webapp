package scratch.simple.webapp.controller;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Map;

import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class UsernameControllerTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void Can_request_the_username() {

        // Given
        final String username = someString();

        // When
        final Map<String, String> actual = new UsernameController().getUsername(username);

        // Then
        assertThat(actual, hasEntry("username", username));
    }
}