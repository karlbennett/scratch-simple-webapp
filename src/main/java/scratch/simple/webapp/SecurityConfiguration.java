package scratch.simple.webapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected final void configure(HttpSecurity http) throws Exception {
        // The CSRF prevention is disabled because it greatly complicates the requirements for the sign in POST request.
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/", "/scripts/*", "/username", "/registration").permitAll()
            .anyRequest().authenticated();
        http.formLogin().defaultSuccessUrl("/").loginPage("/signIn").permitAll();
        http.logout().logoutUrl("/signOut").logoutSuccessUrl("/");
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
}
