package scratch.simple.webapp.step;

import cucumber.scratch.simple.webapp.domain.User;
import cucumber.scratch.simple.webapp.domain.UserFactory;
import cucumber.scratch.simple.webapp.page.HomePage;
import cucumber.scratch.simple.webapp.page.ProfilePage;
import cucumber.scratch.simple.webapp.page.SignInPage;
import cucumber.scratch.simple.webapp.step.Hooks;
import cucumber.scratch.simple.webapp.step.SignInSteps;
import cucumber.scratch.simple.webapp.step.UserHolder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.openqa.selenium.WebDriver;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.openqa.selenium.WebDriver.*;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class HooksTest {

    @Test
    public void Can_sign_out_after_every_scenario() {

        final WebDriver driver = mock(WebDriver.class);

        final Options options = mock(Options.class);

        // Given
        given(driver.manage()).willReturn(options);

        // When
        new Hooks(driver).tearDown();

        // Then
        verify(options).deleteAllCookies();

    }
}
