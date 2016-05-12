package scratch.simple.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import scratch.simple.webapp.data.UserRepository;
import scratch.simple.webapp.domain.User;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/registration")
@SessionAttributes("username")
public class RegistrationController {

    private final UserRepository userRepository;

    @Autowired
    public RegistrationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(method = GET)
    public String register() {
        return "/html/registration.html";
    }

    @RequestMapping(method = POST, consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public String register(User user, Model model) {
        userRepository.save(user);
        model.addAttribute("username", user.getUsername());
        return "redirect:/registration/success";
    }

    @RequestMapping(method = GET, path = "/success")
    public String registrationSuccess() {
        return "/html/registration-success.html";
    }
}
