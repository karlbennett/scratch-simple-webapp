package scratch.simple.webapp.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;
import scratch.simple.webapp.domain.User;

import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class WebComponentControllerTest {

    private WebComponentController controller;

    @Before
    public void setUp() {
        controller = new WebComponentController();
    }

    @Test
    public void Can_request_the_page_header_web_component() {

        // When
        final String actual = controller.pageHeader();

        // Then
        assertThat(actual, is("page-header"));
    }

    @Test
    public void Can_request_the_user_profile_web_component() {

        // Given
        final User user = mock(User.class);

        // When
        final ModelAndView actual = controller.userProfile(user);

        // Then
        assertThat(actual.getViewName(), is("user-profile"));
        assertThat(actual.getModel(), hasEntry("user", (Object) user));
    }

    @Test
    public void Can_request_the_registration_success_message_web_component() {

        // When
        final String actual = controller.registrationSuccessMessage();

        // Then
        assertThat(actual, is("registration-success-message"));
    }
}