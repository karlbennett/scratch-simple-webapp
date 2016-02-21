package scratch.simple.webapp.page;

import cucumber.scratch.simple.webapp.finder.Finders;
import cucumber.scratch.simple.webapp.page.SeleniumHomePage;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class SeleniumHomePageTest {

    private WebDriver driver;
    private String homePageUrl;
    private Finders finders;
    private SeleniumHomePage page;

    @Before
    public void setUp() {
        driver = mock(WebDriver.class);
        homePageUrl = someString();
        finders = mock(Finders.class);
        page = new SeleniumHomePage(driver, homePageUrl, finders);
    }

    @Test
    public void Can_visit_the_home_page() {

        // When
        page.visit();

        // Then
        verify(driver).get(homePageUrl);
    }

    @Test
    public void Can_got_to_the_registration_page() {

        // When
        page.clickRegister();

        // Then
        verify(finders).clickByText("a", "Register");
    }

    @Test
    public void Can_got_to_the_sign_in_page() {

        // When
        page.clickSignIn();

        // Then
        verify(finders).clickByText("a", "Sign In");
    }
}