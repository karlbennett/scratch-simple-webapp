package cucumber.scratch.simple.webapp.step;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.scratch.simple.webapp.domain.UserFactory;
import cucumber.scratch.simple.webapp.page.HomePage;
import cucumber.scratch.simple.webapp.page.ProfilePage;
import cucumber.scratch.simple.webapp.page.SignInPage;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Hooks extends SpringBootSteps {

    private final WebDriver driver;

    @Autowired
    public Hooks(WebDriver driver) {
        this.driver = driver;
    }

    @After
    public void tearDown() {
        driver.manage().deleteAllCookies();
    }
}
