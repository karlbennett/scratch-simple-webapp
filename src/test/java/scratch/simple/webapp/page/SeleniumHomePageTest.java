package scratch.simple.webapp.page;

import cucumber.scratch.simple.webapp.finder.Finders;
import cucumber.scratch.simple.webapp.page.SeleniumHomePage;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
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

    @Test
    public void Can_check_that_the_home_page_is_the_current_page() {

        // Given
        given(driver.getTitle()).willReturn("Simple Webapp (Home)");

        // When
        final boolean actual = page.isCurrentPage();

        // Then
        assertThat(actual, is(true));
    }

    @Test
    public void Can_check_that_the_home_page_is_not_the_current_page() {

        // Given
        given(driver.getTitle()).willReturn(someString());

        // When
        final boolean actual = page.isCurrentPage();

        // Then
        assertThat(actual, is(false));
    }

    @Test
    public void Can_get_the_account_name() {

        final WebElement signIn = mock(WebElement.class);

        final String expected = someString();

        // Given
        given(finders.findByClassName("username")).willReturn(signIn);
        given(signIn.getText()).willReturn(expected);

        // When
        final String actual = page.getAccountName();

        // Then
        assertThat(actual, is(expected));
    }
}