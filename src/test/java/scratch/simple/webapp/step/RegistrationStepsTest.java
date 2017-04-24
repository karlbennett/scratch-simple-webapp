package scratch.simple.webapp.step;

import cucumber.scratch.simple.webapp.domain.User;
import cucumber.scratch.simple.webapp.domain.UserFactory;
import cucumber.scratch.simple.webapp.page.HomePage;
import cucumber.scratch.simple.webapp.page.RegistrationPage;
import cucumber.scratch.simple.webapp.page.RegistrationSuccessPage;
import cucumber.scratch.simple.webapp.step.RegistrationSteps;
import cucumber.scratch.simple.webapp.step.UserHolder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static shiver.me.timbers.data.random.RandomStrings.someNumericString;
import static shiver.me.timbers.data.random.RandomStrings.someString;
import static shiver.me.timbers.data.random.RandomThings.someThing;

public class RegistrationStepsTest {

    private UserFactory userFactory;
    private UserHolder userHolder;
    private HomePage homePage;
    private RegistrationPage registrationPage;
    private RegistrationSteps steps;
    private RegistrationSuccessPage registrationSuccessPage;

    @Before
    public void setUp() {
        userFactory = mock(UserFactory.class);
        userHolder = mock(UserHolder.class);
        homePage = mock(HomePage.class);
        registrationPage = mock(RegistrationPage.class);
        registrationSuccessPage = mock(RegistrationSuccessPage.class);
        steps = new RegistrationSteps(
            userFactory,
            userHolder,
            homePage,
            registrationPage,
            registrationSuccessPage
        );
    }

    @Test
    public void Can_create_the_details_for_a_new_account() {

        final User user = mock(User.class);

        // Given
        given(userFactory.createNew()).willReturn(user);

        // When
        steps.I_am_a_new_user();

        // Then
        verify(userHolder).set(user);
    }

    @Test
    public void Can_register_a_new_account() {

        final User user = mock(User.class);

        // Given
        given(userHolder.get()).willReturn(user);

        // When
        steps.I_register_a_new_account();

        // Then
        final InOrder order = inOrder(homePage, registrationPage);
        order.verify(homePage).visit();
        order.verify(homePage).clickRegister();
        order.verify(registrationPage).register(user);
    }

    @Test
    public void Can_verify_that_we_are_on_the_registration_success_page() {

        final User user = mock(User.class);

        final String username = someNumericString();

        // Given
        given(userHolder.get()).willReturn(user);
        given(user.getUsername()).willReturn(username);
        given(registrationSuccessPage.getWelcome()).willReturn("Welcome");
        given(registrationSuccessPage.getMessage()).willReturn("Your account has been setup. You can now Sign In.");

        // When
        steps.the_registration_should_have_succeeded();

        // Then
        verify(registrationSuccessPage).getWelcome();
    }

    @Test(expected = AssertionError.class)
    public void Can_fail_to_verify_that_we_are_on_the_registration_success_page() {

        final User user = mock(User.class);

        // Given
        given(userHolder.get()).willReturn(user);
        given(user.getUsername()).willReturn(someString());
        given(registrationSuccessPage.getWelcome()).willReturn(someThing(someString(), "Welcome"));
        given(registrationSuccessPage.getMessage()).willReturn(someString());

        // When
        steps.the_registration_should_have_succeeded();
    }
}
