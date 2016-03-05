package scratch.simple.webapp.controller;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Map;

import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class UsernameControllerTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private UsernameController controller;

    @Before
    public void setUp() {
        controller = new UsernameController();
    }

    @Test
    public void Can_get_the_default_username() {

        // When
        final String actual = controller.username();

        // Then
        assertThat(actual, isEmptyString());
    }

    @Test
    public void Can_request_the_username() {

        // Given
        final String username = someString();

        // When
        final Map<String, String> actual = controller.getUsername(username);

        // Then
        assertThat(actual, hasEntry("username", username));
    }
}