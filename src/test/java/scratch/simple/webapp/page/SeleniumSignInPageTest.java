package scratch.simple.webapp.page;

import cucumber.scratch.simple.webapp.domain.User;
import cucumber.scratch.simple.webapp.finder.Finders;
import cucumber.scratch.simple.webapp.page.SeleniumSignInPage;
import org.junit.Test;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class SeleniumSignInPageTest {

    @Test
    public void Can_sign_in() {

        final Finders finders = mock(Finders.class);
        final User user = mock(User.class);

        final String username = someString();
        final String password = someString();

        // Given
        given(user.getUsername()).willReturn(username);
        given(user.getPassword()).willReturn(password);

        // When
        new SeleniumSignInPage(finders).signIn(user);

        // Then
        verify(finders).setTextByLabel("Username", username);
        verify(finders).setTextByLabel("Password", password);
        verify(finders).clickByValue("Sign In");
    }
}