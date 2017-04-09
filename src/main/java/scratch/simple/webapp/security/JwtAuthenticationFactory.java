package scratch.simple.webapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import scratch.simple.webapp.jwt.JwtDecoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Component
public class JwtAuthenticationFactory implements AuthenticationFactory {

    private final JwtDecoder jwtDecoder;

    @Autowired
    public JwtAuthenticationFactory(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public Authentication create(HttpServletRequest request) {
        // We use the second constructor because it set the authentication to be authenticated.
        final String jwtToken = extractJetCookieValue(request);
        if (jwtToken == null) {
            return null;
        }
        return new UsernamePasswordAuthenticationToken(
            jwtDecoder.decodeUsername(jwtToken),
            null,
            null
        );
    }

    private String extractJetCookieValue(HttpServletRequest request) {
        final Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        return Arrays.stream(cookies).filter(c -> c.getName().equals("X-AUTH-TOKEN")).findFirst()
            .map(Cookie::getValue).orElse(null);
    }
}
