package scratch.simple.webapp.controller;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.ModelAndView;
import scratch.simple.webapp.data.UserRepository;
import scratch.simple.webapp.domain.User;
import scratch.simple.webapp.security.SecurityContextHolder;
import scratch.simple.webapp.security.UserDetailsFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static shiver.me.timbers.data.random.RandomStrings.someString;
import static shiver.me.timbers.matchers.Matchers.hasField;
import static shiver.me.timbers.matchers.Matchers.hasFieldThat;

public class RegistrationAutoSignInInterceptorTest {

    private SecurityContextHolder securityContextHolder;
    private UserRepository userRepository;
    private RegistrationAutoSignInInterceptor interceptor;
    private UserDetailsFactory userDetailsFactory;

    @Before
    public void setUp() {
        securityContextHolder = mock(SecurityContextHolder.class);
        userRepository = mock(UserRepository.class);
        userDetailsFactory = mock(UserDetailsFactory.class);
        interceptor = new RegistrationAutoSignInInterceptor(userRepository, userDetailsFactory, securityContextHolder);
    }

    @Test
    public void Can_auto_sign_in_after_a_successful_registration() throws Exception {

        final HttpServletRequest request = mock(HttpServletRequest.class);

        final SecurityContext securityContext = mock(SecurityContext.class);
        final HttpSession session = mock(HttpSession.class);
        final String username = someString();
        final User user = mock(User.class);
        final UserDetails userDetails = mock(UserDetails.class);

        // Given
        given(request.getPathInfo()).willReturn("/registration");
        given(request.getMethod()).willReturn("POST");
        given(securityContextHolder.getContext()).willReturn(securityContext);
        given(request.getSession()).willReturn(session);
        given(session.getAttribute("username")).willReturn(username);
        given(userRepository.findByUsername(username)).willReturn(user);
        given(userDetailsFactory.create(user)).willReturn(userDetails);

        // When
        interceptor.postHandle(request, mock(HttpServletResponse.class), new Object(), mock(ModelAndView.class));

        // Then
        verify(securityContext).setAuthentication(
            argThat(Matchers.<Authentication>allOf(
                instanceOf(UsernamePasswordAuthenticationToken.class),
                hasField("principal", userDetails),
                hasFieldThat("credentials", nullValue()),
                hasField("authenticated", true)
            ))
        );
    }

    @Test
    public void Will_not_auto_sign_in_after_a_successful_registration_page_request() throws Exception {

        final HttpServletRequest request = mock(HttpServletRequest.class);

        // Given
        given(request.getPathInfo()).willReturn("/registration");
        given(request.getMethod()).willReturn("GET");

        // When
        interceptor.postHandle(request, mock(HttpServletResponse.class), new Object(), mock(ModelAndView.class));

        // Then
        verify(securityContextHolder, never()).getContext();
        verify(request, never()).getSession();
    }

    @Test
    public void Will_not_auto_sign_in_after_any_other_request() throws Exception {

        final HttpServletRequest request = mock(HttpServletRequest.class);

        // Given
        given(request.getPathInfo()).willReturn(someString());
        given(request.getMethod()).willReturn("POST");

        // When
        interceptor.postHandle(request, mock(HttpServletResponse.class), new Object(), mock(ModelAndView.class));

        // Then
        verify(securityContextHolder, never()).getContext();
        verify(request, never()).getSession();
    }
}