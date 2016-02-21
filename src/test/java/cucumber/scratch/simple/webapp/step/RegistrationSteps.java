package cucumber.scratch.simple.webapp.step;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.scratch.simple.webapp.domain.UserFactory;
import cucumber.scratch.simple.webapp.page.HomePage;
import cucumber.scratch.simple.webapp.page.ProfilePage;
import cucumber.scratch.simple.webapp.page.RegistrationPage;
import cucumber.scratch.simple.webapp.page.RegistrationSuccessPage;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class RegistrationSteps extends SpringBootSteps {

    private final UserFactory userFactory;
    private final UserHolder userHolder;
    private final HomePage homePage;
    private final RegistrationPage registrationPage;
    private final RegistrationSuccessPage registrationSuccessPage;
    private final ProfilePage profilePage;

    @Autowired
    public RegistrationSteps(
        UserFactory userFactory,
        UserHolder userHolder,
        HomePage homePage,
        RegistrationPage registrationPage,
        RegistrationSuccessPage registrationSuccessPage,
        ProfilePage profilePage
    ) {
        this.userFactory = userFactory;
        this.userHolder = userHolder;
        this.homePage = homePage;
        this.registrationPage = registrationPage;
        this.registrationSuccessPage = registrationSuccessPage;
        this.profilePage = profilePage;
    }

    @Given("^I am a new user$")
    public void I_am_a_new_user() {
        userHolder.set(userFactory.createNew());
    }

    @When("^I register a new account$")
    public void I_register_a_new_account() {
        homePage.visit();
        homePage.clickRegister();
        registrationPage.register(userHolder.get());
    }

    @Then("^the registration should have succeeded$")
    public void the_registration_should_have_succeeded() {
        assertThat(registrationSuccessPage.getWelcome(), equalTo("Welcome " + userHolder.get().getUsername()));
    }

    @Then("^I can navigate to my profile$")
    public void I_can_navigate_to_my_profile() {
        registrationSuccessPage.clickProfile();
        assertThat(profilePage.getUsername(), equalTo(userHolder.get().getUsername()));
    }
}