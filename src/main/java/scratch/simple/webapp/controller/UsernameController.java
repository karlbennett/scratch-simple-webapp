package scratch.simple.webapp.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import scratch.simple.webapp.domain.User;

import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/username")
@SessionAttributes("user")
public class UsernameController {

    @RequestMapping(produces = APPLICATION_JSON_VALUE)
    public Map<String, String> getUsername(User user) {
        if (user.getUsername() != null) {
            return singletonMap("username", user.getUsername());
        }

        throw new IllegalArgumentException("The username could not be requested.");
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public Map<String, String> handle(IllegalArgumentException exception) {
        return singletonMap("message", exception.getMessage());
    }
}
