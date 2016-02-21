package scratch.simple.webapp.security;

import org.springframework.security.core.context.SecurityContext;

public interface SecurityContextHolder {

    SecurityContext getContext();
}
