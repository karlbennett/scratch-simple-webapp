package scratch.simple.webapp.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import scratch.simple.webapp.jwt.JwtEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtEncoder jwtEncoder;
    private final AuthenticationSuccessHandler delegate;

    public JwtAuthenticationSuccessHandler(JwtEncoder jwtEncoder, AuthenticationSuccessHandler delegate) {
        this.jwtEncoder = jwtEncoder;
        this.delegate = delegate;
    }

    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
    ) throws IOException, ServletException {
        final Cookie cookie = new Cookie("X-AUTH-TOKEN", jwtEncoder.encodeUsername(authentication.getName()));
        cookie.setComment("Authentication token for the Simple Webapp.");
        cookie.setPath("/");
        cookie.setMaxAge(-1); // Session cookie. Will be deleted on browser close.
        cookie.setSecure(false); // Is insecure so that this can be easily run locally.
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        delegate.onAuthenticationSuccess(request, response, authentication);
    }
}
