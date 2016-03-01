package scratch.simple.webapp.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import scratch.simple.webapp.data.UserRepository;
import scratch.simple.webapp.domain.User;
import scratch.simple.webapp.security.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationAutoSignInInterceptor extends HandlerInterceptorAdapter {

    private final UserRepository userRepository;
    private final SecurityContextHolder securityContextHolder;

    public RegistrationAutoSignInInterceptor(
        UserRepository userRepository,
        SecurityContextHolder securityContextHolder
    ) {
        this.userRepository = userRepository;
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

        final User user = userRepository.findByUsername((String) request.getSession().getAttribute("username"));
        securityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
            user.getUsername(),
            user.getPassword()
        ));
    }
}
