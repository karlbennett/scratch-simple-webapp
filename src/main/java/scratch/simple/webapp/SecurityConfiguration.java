package scratch.simple.webapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import scratch.simple.webapp.jwt.JwtEncoder;
import scratch.simple.webapp.security.AuthenticationFactory;
import scratch.simple.webapp.security.JwtAuthenticationFilter;
import scratch.simple.webapp.security.JwtAuthenticationSuccessHandler;
import scratch.simple.webapp.security.JwtLogoutSuccessHandler;
import scratch.simple.webapp.security.SecurityContextHolder;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationFactory authenticationFactory;

    @Autowired
    private SecurityContextHolder securityContextHolder;

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected final void configure(HttpSecurity http) throws Exception {
        // The CSRF prevention is disabled because it greatly complicates the requirements for the sign in POST request.
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests()
            .antMatchers(
                "/",
                "/favicon.ico",
                "/scripts/*",
                "/css/*",
                "/registration",
                "/registration/success"
            ).permitAll()
            .anyRequest().authenticated();
        http.addFilterBefore(
            new JwtAuthenticationFilter(authenticationFactory, securityContextHolder),
            UsernamePasswordAuthenticationFilter.class
        );
        http.formLogin()
            .successHandler(new JwtAuthenticationSuccessHandler(
                jwtEncoder,
                new SimpleUrlAuthenticationSuccessHandler("/")
            ))
            .loginPage("/signIn").permitAll();
        http.logout()
            .logoutSuccessHandler(new JwtLogoutSuccessHandler(new SimpleUrlLogoutSuccessHandler()))
            .logoutUrl("/signOut").logoutSuccessUrl("/");
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
}
