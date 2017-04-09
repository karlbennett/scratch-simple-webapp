package scratch.simple.webapp.jwt;

import io.jsonwebtoken.JwtParser;

public interface JwtParserFactory {
    JwtParser create();
}
