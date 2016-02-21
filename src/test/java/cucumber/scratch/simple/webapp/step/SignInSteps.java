package cucumber.scratch.simple.webapp.step;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.scratch.simple.webapp.domain.UserFactory;
import cucumber.scratch.simple.webapp.page.HomePage;
import cucumber.scratch.simple.webapp.page.SignInPage;
import org.springframework.beans.factory.annotation.Autowired;

public class SignInSteps extends SpringBootSteps {

    private final UserFactory userFactory;
    private final UserHolder userHolder;
    private final HomePage homePage;
    private final SignInPage signInPage;

    @Autowired
    public SignInSteps(UserFactory userFactory, UserHolder userHolder, HomePage homePage, SignInPage signInPage) {
        this.userFactory = userFactory;
        this.userHolder = userHolder;
        this.homePage = homePage;
        this.signInPage = signInPage;
    }

    @Given("^I am an existing user$")
    public void I_am_an_existing_user() {
        userHolder.set(userFactory.createExisting());
    }

    @When("^I sign in$")
    public void I_sign_in() {
        homePage.visit();
        homePage.clickSignIn();
        signInPage.signIn(userHolder.get());
    }
}
