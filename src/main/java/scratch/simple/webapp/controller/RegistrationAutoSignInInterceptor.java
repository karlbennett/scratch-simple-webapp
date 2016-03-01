package scratch.simple.webapp.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import scratch.simple.webapp.data.UserRepository;
import scratch.simple.webapp.security.SecurityContextHolder;
import scratch.simple.webapp.security.UserDetailsFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationAutoSignInInterceptor extends HandlerInterceptorAdapter {

    private final UserRepository userRepository;
    private final UserDetailsFactory userDetailsFactory;
    private final SecurityContextHolder securityContextHolder;

    public RegistrationAutoSignInInterceptor(
        UserRepository userRepository,
        UserDetailsFactory userDetailsFactory,
        SecurityContextHolder securityContextHolder
    ) {
        this.userRepository = userRepository;
        this.userDetailsFactory = userDetailsFactory;
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

        securityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
            userDetailsFactory.create(
                userRepository.findByUsername((String) request.getSession().getAttribute("username"))
            ),
            null,
            null
        ));
    }
}
