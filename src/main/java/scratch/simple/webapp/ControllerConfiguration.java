package scratch.simple.webapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import scratch.simple.webapp.controller.ProfileUserArgumentResolver;
import scratch.simple.webapp.controller.RegistrationAutoSignInInterceptor;
import scratch.simple.webapp.data.UserRepository;
import scratch.simple.webapp.security.SecurityContextHolder;

import java.util.List;

@Configuration
public class ControllerConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityContextHolder securityContextHolder;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("/html/homepage.html");
        registry.addViewController("/signIn").setViewName("/html/signIn.html");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new ProfileUserArgumentResolver(userRepository));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RegistrationAutoSignInInterceptor(userRepository, securityContextHolder));
    }
}
