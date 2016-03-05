package scratch.simple.webapp.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class UsernameSessionInterceptorTest {

    private UsernameSessionInterceptor interceptor;

    @Before
    public void setUp() {
        interceptor = new UsernameSessionInterceptor();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void Can_create_a_new_session_before_a_username_controller_get_request() throws Exception {

        final HandlerMethod handlerMethod = mock(HandlerMethod.class);
        final HttpServletRequest request = mock(HttpServletRequest.class);

        // Given
        given(handlerMethod.getBeanType()).willReturn((Class) UsernameController.class);
        given(handlerMethod.getMethod()).willReturn(UsernameController.class.getMethod("getUsername", String.class));
        given(request.getMethod()).willReturn("GET");

        // When
        final boolean actual = interceptor.preHandle(request, null, handlerMethod);

        // Then
        assertThat(actual, is(true));
        verify(request).getSession(true);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void Will_not_create_the_session_before_a_unannotated_controller() throws Exception {

        // Given
        final HttpServletRequest request = mock(HttpServletRequest.class);

        // When
        final boolean actual = interceptor.preHandle(request, null, new Object());

        // Then
        assertThat(actual, is(true));
        verify(request, never()).getSession(true);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void Will_not_create_the_session_before_any_other_controller() throws Exception {

        final HandlerMethod handlerMethod = mock(HandlerMethod.class);
        final HttpServletRequest request = mock(HttpServletRequest.class);

        // Given
        given(handlerMethod.getBeanType()).willReturn((Class) Object.class);
        given(handlerMethod.getMethod()).willReturn(UsernameController.class.getMethod("getUsername", String.class));
        given(request.getMethod()).willReturn("GET");

        // When
        final boolean actual = interceptor.preHandle(request, null, handlerMethod);

        // Then
        assertThat(actual, is(true));
        verify(request, never()).getSession(true);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void Will_not_create_the_session_before_any_other_type_of_username_controller_method() throws Exception {

        final HandlerMethod handlerMethod = mock(HandlerMethod.class);
        final HttpServletRequest request = mock(HttpServletRequest.class);

        // Given
        given(handlerMethod.getBeanType()).willReturn((Class) UsernameController.class);
        given(handlerMethod.getMethod()).willReturn(UsernameController.class.getMethod("username"));
        given(request.getMethod()).willReturn("GET");

        // When
        final boolean actual = interceptor.preHandle(request, null, handlerMethod);

        // Then
        assertThat(actual, is(true));
        verify(request, never()).getSession(true);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void Will_not_create_the_session_before_any_other_type_of_username_controller_request() throws Exception {

        final HandlerMethod handlerMethod = mock(HandlerMethod.class);
        final HttpServletRequest request = mock(HttpServletRequest.class);

        // Given
        given(handlerMethod.getBeanType()).willReturn((Class) UsernameController.class);
        given(handlerMethod.getMethod()).willReturn(UsernameController.class.getMethod("getUsername", String.class));
        given(request.getMethod()).willReturn(someString());

        // When
        final boolean actual = interceptor.preHandle(request, null, handlerMethod);

        // Then
        assertThat(actual, is(true));
        verify(request, never()).getSession(true);
    }
}