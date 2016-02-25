package scratch.simple.webapp.controller;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import scratch.simple.webapp.domain.User;

import java.util.Map;

import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class UsernameControllerTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void Can_request_the_username() {

        final User user = mock(User.class);

        final String username = someString();

        // Given
        given(user.getUsername()).willReturn(username);

        // When
        final Map<String, String> actual = new UsernameController().getUsername(user);

        // Then
        assertThat(actual, hasEntry("username", username));
    }

    @Test
    public void Cannot_request_an_empty_username() {

        final User user = mock(User.class);

        // Given
        given(user.getUsername()).willReturn(null);
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("The username could not be requested.");

        // When
        new UsernameController().getUsername(user);
    }

    @Test
    public void Can_handle_an_illegal_argument_exception() {

        // Given
        final String message = someString();
        final IllegalArgumentException exception = new IllegalArgumentException(message);

        // When
        final Map<String, String> actual = new UsernameController().handle(exception);

        // Then
        assertThat(actual, hasEntry("message", message));
    }
}