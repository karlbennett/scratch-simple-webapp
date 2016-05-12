package scratch.simple.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import scratch.simple.webapp.domain.User;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("/webcomponent")
@SessionAttributes("username")
public class WebComponentController {

    @RequestMapping(path = "/page-header", method = GET)
    public String pageHeader() {
        return "page-header";
    }

    @RequestMapping(path = "/user-profile/{username}", method = GET)
    public ModelAndView userProfile(User user) {
        return new ModelAndView("user-profile").addObject("user", user);
    }

    @RequestMapping(path = "/registration-success-message", method = GET)
    public String registrationSuccessMessage() {
        return "registration-success-message";
    }
}
