package scratch.simple.webapp.page;

import cucumber.scratch.simple.webapp.finder.Finders;
import cucumber.scratch.simple.webapp.page.SeleniumRegistrationSuccessPage;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class SeleniumRegistrationSuccessPageTest {

    private Finders finders;
    private SeleniumRegistrationSuccessPage page;

    @Before
    public void setUp() {
        finders = mock(Finders.class);
        page = new SeleniumRegistrationSuccessPage(finders);
    }

    @Test
    public void Can_get_the_welcome_message() {

        final WebElement welcome = mock(WebElement.class);
        final String expected = someString();

        // Given
        given(finders.findByText("h1", "Welcome")).willReturn(welcome);
        given(welcome.getText()).willReturn(expected);

        // When
        final String actual = page.getWelcome();

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_get_the_profile_link() {

        // When
        page.clickProfile();

        // Then
        verify(finders).clickByText("a", "profile");
    }
}