package scratch.simple.webapp.step;

import cucumber.scratch.simple.webapp.domain.User;
import cucumber.scratch.simple.webapp.domain.UserFactory;
import cucumber.scratch.simple.webapp.page.HomePage;
import cucumber.scratch.simple.webapp.page.SignInPage;
import cucumber.scratch.simple.webapp.step.SignInSteps;
import cucumber.scratch.simple.webapp.step.UserHolder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SignInStepsTest {

    private UserFactory userFactory;
    private UserHolder userHolder;
    private HomePage homePage;
    private SignInPage signInPage;
    private SignInSteps inSteps;

    @Before
    public void setUp() {
        userFactory = mock(UserFactory.class);
        userHolder = mock(UserHolder.class);
        homePage = mock(HomePage.class);
        signInPage = mock(SignInPage.class);
        inSteps = new SignInSteps(userFactory, userHolder, homePage, signInPage);
    }

    @Test
    public void Can_create_the_details_for_an_existing_account() {

        final User user = mock(User.class);

        // Given
        given(userFactory.createExisting()).willReturn(user);

        // When
        inSteps.I_am_an_existing_user();

        // Then
        verify(userHolder).set(user);
    }

    @Test
    public void Can_sign_in_to_an_existing_account() {

        final User user = mock(User.class);

        // Given
        given(userHolder.get()).willReturn(user);

        // When
        inSteps.I_sign_in();

        // Then
        final InOrder order = inOrder(homePage, signInPage);
        order.verify(homePage).visit();
        order.verify(homePage).clickSignIn();
        order.verify(signInPage).signIn(user);
    }
}
