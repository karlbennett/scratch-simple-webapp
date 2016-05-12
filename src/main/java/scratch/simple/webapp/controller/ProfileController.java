package scratch.simple.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/profile")
public class ProfileController {

    @RequestMapping(path = "/{username}")
    public ModelAndView profile(@PathVariable String username) {
        return new ModelAndView("profile").addObject("username", username);
    }
}
