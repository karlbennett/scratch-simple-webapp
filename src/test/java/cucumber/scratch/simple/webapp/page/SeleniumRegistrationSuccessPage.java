package cucumber.scratch.simple.webapp.page;

import cucumber.scratch.simple.webapp.finder.Finders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SeleniumRegistrationSuccessPage implements RegistrationSuccessPage {

    private final Finders finders;

    @Autowired
    public SeleniumRegistrationSuccessPage(Finders finders) {
        this.finders = finders;
    }

    @Override
    public String getWelcome() {
        return finders.findByText("h1", "Welcome").getText();
    }

    @Override
    public void clickProfile() {
        finders.clickByText("a", "profile");
    }
}
