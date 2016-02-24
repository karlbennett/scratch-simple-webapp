package cucumber.scratch.simple.webapp.step;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.scratch.simple.webapp.domain.UserFactory;
import cucumber.scratch.simple.webapp.page.HomePage;
import cucumber.scratch.simple.webapp.page.SignInPage;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

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

    @Then("^I should be on the homepage$")
    public void I_should_be_on_the_homepage() {
        assertThat("Should be on the home page.", homePage.isCurrentPage(), is(true));
    }

    @Then("^I should see that I am signed in$")
    public void I_should_see_that_I_am_signed_in() {
        assertThat("Should see username on the homepage.", homePage.getAccountName(), is(userHolder.get().getUsername()));
    }
}