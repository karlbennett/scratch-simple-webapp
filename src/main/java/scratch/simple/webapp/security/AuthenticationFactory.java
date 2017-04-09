package scratch.simple.webapp.security;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationFactory {

    Authentication create(HttpServletRequest request);
}
