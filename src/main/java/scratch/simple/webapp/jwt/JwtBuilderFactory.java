package scratch.simple.webapp.jwt;

import io.jsonwebtoken.JwtBuilder;

public interface JwtBuilderFactory {
    JwtBuilder create();
}
