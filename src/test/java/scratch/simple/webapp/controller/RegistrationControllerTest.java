package scratch.simple.webapp.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;
import scratch.simple.webapp.data.UserRepository;
import scratch.simple.webapp.domain.User;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class RegistrationControllerTest {

    private UserRepository userRepository;
    private RegistrationController controller;

    @Before
    public void setUp() {
        userRepository = mock(UserRepository.class);
        controller = new RegistrationController(userRepository);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void Can_request_the_registration_page() {

        // When
        final String actual = controller.register();

        // Then
        assertThat(actual, equalTo("/html/registration.html"));
        verifyZeroInteractions(userRepository);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void Can_register_a_new_user() {

        final User user = mock(User.class);
        final Model model = mock(Model.class);

        final String username = someString();

        // Given
        given(user.getUsername()).willReturn(username);

        // When
        final String actual = controller.register(user, model);

        // Then
        assertThat(actual, equalTo("redirect:/registration/success"));
        verify(userRepository).save(user);
        verify(model).addAttribute("username", username);
    }

    @Test
    public void Can_render_the_registration_success_page() {

        // When
        final String actual = controller.registrationSuccess();

        // Then
        assertThat(actual, is("/html/registration-success.html"));
        verifyZeroInteractions(userRepository);
    }
}