package cucumber.scratch.simple.webapp.step;

import cucumber.scratch.simple.webapp.domain.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("cucumber-glue")
public class UserHolder extends GenericHolder<User> {
}
