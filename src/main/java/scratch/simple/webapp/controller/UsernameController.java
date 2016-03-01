package scratch.simple.webapp.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/username")
@SessionAttributes("username")
public class UsernameController {

    @RequestMapping(produces = APPLICATION_JSON_VALUE)
    public Map<String, String> getUsername(@ModelAttribute("username") String username) {
        return singletonMap("username", username);
    }
}
