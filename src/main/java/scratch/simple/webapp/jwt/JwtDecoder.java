package scratch.simple.webapp.jwt;

public interface JwtDecoder {
    String decodeUsername(String jwtToken);
}
