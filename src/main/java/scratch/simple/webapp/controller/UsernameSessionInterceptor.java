package scratch.simple.webapp.controller;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This interceptor is here to make sure that the session exists before entering the {@link UsernameController}. This
 * must be done because the controllers method uses {@link @org.springframework.web.bind.annotation.ResponseBody} so
 * Spring will attempt to create the session after the response has been closed. So we have to make sure the session
 * already exists to prevent any errors.
 */
public class UsernameSessionInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (isUsernameRequest(request, handler)) {
            request.getSession(true);
        }

        return true;
    }

    private static boolean isUsernameRequest(HttpServletRequest request, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return false;
        }

        final HandlerMethod handlerMethod = (HandlerMethod) handler;

        if (!UsernameController.class.equals(handlerMethod.getBeanType())) {
            return false;
        }

        if (!"getUsername".equals(handlerMethod.getMethod().getName())) {
            return false;
        }

        if (!"GET".equals(request.getMethod())) {
            return false;
        }

        return true;
    }
}
