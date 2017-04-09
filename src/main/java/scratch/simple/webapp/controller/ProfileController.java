package scratch.simple.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import scratch.simple.webapp.domain.User;

@Controller
@RequestMapping(path = "/profile")
public class ProfileController {

    @RequestMapping
    public ModelAndView profile(User user) {
        return new ModelAndView("profile")
            .addObject("username", user.getUsername())
            .addObject("user", user);
    }
}
