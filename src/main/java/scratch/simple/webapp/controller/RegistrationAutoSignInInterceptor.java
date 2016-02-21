package scratch.simple.webapp.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import scratch.simple.webapp.domain.User;
import scratch.simple.webapp.security.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationAutoSignInInterceptor extends HandlerInterceptorAdapter {

    private final SecurityContextHolder securityContextHolder;

    public RegistrationAutoSignInInterceptor(SecurityContextHolder securityContextHolder) {
        this.securityContextHolder = securityContextHolder;
    }

    @Override
    public void postHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler,
        ModelAndView modelAndView
    ) throws Exception {
        if (!"/registration".equals(request.getPathInfo()) || !"POST".equals(request.getMethod())) {
            return;
        }

        final User user = (User) request.getSession().getAttribute("user");
        securityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
            user.getUsername(),
            user.getPassword()
        ));
    }
}
