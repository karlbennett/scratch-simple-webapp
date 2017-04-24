package scratch.simple.webapp.controller;

import org.junit.Before;
import org.junit.Test;
import scratch.simple.webapp.data.UserRepository;
import scratch.simple.webapp.domain.User;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

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
        assertThat(actual, equalTo("registration"));
        verifyZeroInteractions(userRepository);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void Can_register_a_new_user() {

        // Given
        final User user = mock(User.class);

        // When
        final String actual = controller.register(user);

        // Then
        assertThat(actual, equalTo("redirect:/registration/success"));
        verify(userRepository).save(user);
    }

    @Test
    public void Can_render_the_registration_success_page() {

        // When
        final String actual = controller.registrationSuccess();

        // Then
        assertThat(actual, equalTo("registration-success"));
    }
}