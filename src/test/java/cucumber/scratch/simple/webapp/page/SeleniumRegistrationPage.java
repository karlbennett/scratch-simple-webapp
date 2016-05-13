package cucumber.scratch.simple.webapp.page;

import cucumber.scratch.simple.webapp.domain.User;
import cucumber.scratch.simple.webapp.finder.Finders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import shiver.me.timbers.waiting.Wait;

@Component
public class SeleniumRegistrationPage implements RegistrationPage {

    private final Finders finders;

    @Autowired
    public SeleniumRegistrationPage(Finders finders) {
        this.finders = finders;
    }

    @Wait
    @Override
    public void register(User user) {
        finders.setTextByLabel("Username", user.getUsername());
        finders.setTextByLabel("Password", user.getPassword());
        finders.clickByValue("Register");
    }
}
