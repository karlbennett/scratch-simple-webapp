package scratch.simple.webapp.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import scratch.simple.webapp.data.UserRepository;
import scratch.simple.webapp.domain.User;

import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;
import static org.springframework.web.servlet.HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE;
import static shiver.me.timbers.data.random.RandomStrings.someString;

public class ProfileUserArgumentResolverTest {

    private UserRepository userRepository;
    private ProfileUserArgumentResolver resolver;

    @Before
    public void setUp() {
        userRepository = mock(UserRepository.class);
        resolver = new ProfileUserArgumentResolver(userRepository);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void Can_support_resolving_the_user_argument_for_the_profile_controller() {

        final MethodParameter parameter = mock(MethodParameter.class);

        // Given
        given(parameter.getDeclaringClass()).willReturn((Class) ProfileController.class);
        given(parameter.getParameterType()).willReturn((Class) User.class);

        // When
        final boolean actual = resolver.supportsParameter(parameter);

        // Then
        assertThat(actual, is(true));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void Cannot_support_resolving_the_user_argument_for_any_other_controller() {

        final MethodParameter methodParameter = mock(MethodParameter.class);

        // Given
        given(methodParameter.getDeclaringClass()).willReturn((Class) RegistrationController.class);
        given(methodParameter.getParameterType()).willReturn((Class) User.class);

        // When
        final boolean actual = resolver.supportsParameter(methodParameter);

        // Then
        assertThat(actual, is(false));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void Can_support_resolving_any_other_argument_for_the_profile_controller() {

        final MethodParameter parameter = mock(MethodParameter.class);

        // Given
        given(parameter.getDeclaringClass()).willReturn((Class) ProfileController.class);
        given(parameter.getParameterType()).willReturn((Class) Long.class);

        // When
        final boolean actual = resolver.supportsParameter(parameter);

        // Then
        assertThat(actual, is(false));
    }

    @Test
    public void Can_resolve_the_user_argument_for_the_profile_controller() throws Exception {

        final NativeWebRequest request = mock(NativeWebRequest.class);

        @SuppressWarnings("unchecked")
        final Map<String, String> pathVariables = mock(Map.class);
        final String username = someString();

        final User expected = mock(User.class);

        // Given
        given(request.getAttribute(URI_TEMPLATE_VARIABLES_ATTRIBUTE, SCOPE_REQUEST)).willReturn(pathVariables);
        given(pathVariables.get("username")).willReturn(username);
        given(userRepository.findByUsername(username)).willReturn(expected);

        // When
        final Object actual = resolver.resolveArgument(
            mock(MethodParameter.class),
            mock(ModelAndViewContainer.class),
            request,
            mock(WebDataBinderFactory.class)
        );

        // Then
        assertThat(actual, is((Object) expected));
    }

    @Test
    public void Will_not_resolve_anything_if_no_path_variables_are_present() throws Exception {

        final NativeWebRequest request = mock(NativeWebRequest.class);

        // Given
        given(request.getAttribute(URI_TEMPLATE_VARIABLES_ATTRIBUTE, SCOPE_REQUEST)).willReturn(null);

        // When
        final Object actual = resolver.resolveArgument(
            mock(MethodParameter.class),
            mock(ModelAndViewContainer.class),
            request,
            mock(WebDataBinderFactory.class)
        );

        // Then
        assertThat(actual, nullValue());
        verify(userRepository, never()).findByUsername(anyString());
    }

    @Test
    public void Will_not_resolve_anything_if_no_username_path_variable_is_present() throws Exception {

        final NativeWebRequest request = mock(NativeWebRequest.class);

        @SuppressWarnings("unchecked")
        final Map<String, String> pathVariables = mock(Map.class);

        // Given
        given(request.getAttribute(URI_TEMPLATE_VARIABLES_ATTRIBUTE, SCOPE_REQUEST)).willReturn(pathVariables);
        given(pathVariables.get("username")).willReturn(null);

        // When
        final Object actual = resolver.resolveArgument(
            mock(MethodParameter.class),
            mock(ModelAndViewContainer.class),
            request,
            mock(WebDataBinderFactory.class)
        );

        // Then
        assertThat(actual, nullValue());
        verify(userRepository, never()).findByUsername(anyString());
    }
}