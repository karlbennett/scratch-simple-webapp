package cucumber.scratch.simple.webapp.step;

import cucumber.api.java.After;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

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
