package scratch.simple.webapp.step;

import cucumber.scratch.simple.webapp.step.Hooks;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.openqa.selenium.WebDriver.Options;

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
