package scratch.simple.webapp.controller;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class ProfileControllerTest {

    @Test
    public void Can_view_a_users_profile() {

        // Given
        final String username = someString();

        // When
        final ModelAndView actual = new ProfileController().profile(username);

        // Then
        assertThat(actual.getViewName(), equalTo("profile"));
        assertThat(actual.getModel(), hasEntry("username", (Object) username));
    }
}