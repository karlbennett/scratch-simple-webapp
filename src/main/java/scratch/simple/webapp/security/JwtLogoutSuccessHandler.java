package scratch.simple.webapp.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {

    private final LogoutSuccessHandler logoutDelegate;

    public JwtLogoutSuccessHandler(LogoutSuccessHandler logoutDelegate) {
        this.logoutDelegate = logoutDelegate;
    }

    @Override
    public void onLogoutSuccess(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
    ) throws IOException, ServletException {
        final Cookie cookie = new Cookie("X-AUTH-TOKEN", "");
        cookie.setComment("Authentication token for the Simple Webapp.");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setSecure(false);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        logoutDelegate.onLogoutSuccess(request, response, authentication);
    }
}
