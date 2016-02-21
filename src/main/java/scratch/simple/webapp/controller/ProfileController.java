package scratch.simple.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import scratch.simple.webapp.domain.User;

@Controller
@SessionAttributes("user")
@RequestMapping(path = "/profile")
public class ProfileController {

    @RequestMapping(path = "/{username}")
    public ModelAndView profile(User user) {
        return new ModelAndView("profile", "user", user);
    }
}
