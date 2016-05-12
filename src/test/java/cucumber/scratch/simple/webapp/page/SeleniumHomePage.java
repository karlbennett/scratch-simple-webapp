package cucumber.scratch.simple.webapp.page;

import cucumber.scratch.simple.webapp.finder.Finders;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import shiver.me.timbers.waiting.Wait;

@Component
@Wait
public class SeleniumHomePage implements HomePage {

    private final WebDriver driver;
    private final String homePageUrl;
    private final Finders finders;

    @Autowired
    public SeleniumHomePage(WebDriver driver, @Value("${home.page.url}") String homePageUrl, Finders finders) {
        this.driver = driver;
        this.homePageUrl = homePageUrl;
        this.finders = finders;
    }

    @Override
    public void visit() {
        driver.get(homePageUrl);
    }

    @Override
    public void clickRegister() {
        finders.clickByText("a", "Register");
    }

    @Override
    public void clickSignIn() {
        finders.clickByText("a", "Sign In");
    }

    @Override
    public boolean isCurrentPage() {
        return "Simple Webapp (Home)".equals(driver.getTitle());
    }

    @Override
    public String getUsername() {
        return finders.findByClassName("username").getText();
    }
}
