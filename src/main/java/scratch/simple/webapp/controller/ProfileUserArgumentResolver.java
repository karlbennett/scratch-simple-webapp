package scratch.simple.webapp.controller;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import scratch.simple.webapp.data.UserRepository;
import scratch.simple.webapp.domain.User;

import java.util.Map;

import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;
import static org.springframework.web.servlet.HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE;

public class ProfileUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserRepository userRepository;

    public ProfileUserArgumentResolver(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return ProfileController.class.equals(parameter.getDeclaringClass()) &&
            User.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(
        MethodParameter parameter,
        ModelAndViewContainer mavContainer,
        NativeWebRequest request,
        WebDataBinderFactory binderFactory
    ) throws Exception {
        @SuppressWarnings("unchecked")
        final Map<String, String> pathVariables = (Map<String, String>) request
            .getAttribute(URI_TEMPLATE_VARIABLES_ATTRIBUTE, SCOPE_REQUEST);

        if (pathVariables == null) {
            return null;
        }

        final String username = pathVariables.get("username");
        if (username == null) {
            return null;
        }

        return userRepository.findByUsername(username);
    }
}
