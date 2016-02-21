package scratch.simple.webapp.page;

import cucumber.scratch.simple.webapp.domain.User;
import cucumber.scratch.simple.webapp.finder.Finders;
import cucumber.scratch.simple.webapp.page.SeleniumRegistrationPage;
import org.junit.Test;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class SeleniumRegistrationPageTest {

    @Test
    public void Can_register_a_new_account() {

        final Finders finders = mock(Finders.class);

        final User user = mock(User.class);

        final String username = someString();
        final String password = someString();

        // Given
        given(user.getUsername()).willReturn(username);
        given(user.getPassword()).willReturn(password);

        // When
        new SeleniumRegistrationPage(finders).register(user);

        // Then
        verify(finders).setTextByLabel("Username", username);
        verify(finders).setTextByLabel("Password", password);
        verify(finders).clickByValue("Register");

    }
}