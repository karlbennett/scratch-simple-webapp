package scratch.simple.webapp.security;

import org.junit.Test;
import org.springframework.security.core.userdetails.UserDetails;
import scratch.simple.webapp.domain.User;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class SSWUserDetailsFactoryTest {

    @Test
    public void Can_create_a_user_details() {

        final User user = mock(User.class);

        final String username = someString();
        final String password = someString();

        // Given
        given(user.getUsername()).willReturn(username);
        given(user.getPassword()).willReturn(password);

        // When
        final UserDetails actual = new SSWUserDetailsFactory().create(user);

        // Then
        assertThat(actual.getUsername(), is(username));
        assertThat(actual.getPassword(), is(password));
    }
}