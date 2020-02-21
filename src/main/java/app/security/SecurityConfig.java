package app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.session.CompositeSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;

import java.util.LinkedList;
import java.util.List;

/**
 * This class is the main security configuration class. For more information of
 * how to configure the security, go to
 * https://docs.spring.io/spring-security/site/docs/current/reference/html/jc.html
 *
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthUserDetailsService userDetailsService;

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers( "/", "/login", "/VAADIN/**", "/HEARTBEAT/**", "/UIDL/**", "/resources/**", "/frontend/**", "/h2-console/**", "docprint").permitAll()
//                .anyRequest().authenticated()
                .and()
                .logout()
                .permitAll()
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()
                .and()
                .authorizeRequests()
                .antMatchers("/main").authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
        ;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder encoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SessionAuthenticationStrategy sessionControlAuthenticationStrategy(){
        SessionFixationProtectionStrategy sessionFixationProtectionStrategy = new SessionFixationProtectionStrategy();
        sessionFixationProtectionStrategy.setMigrateSessionAttributes(false);

        RegisterSessionAuthenticationStrategy registerSessionAuthenticationStrategy = new RegisterSessionAuthenticationStrategy(sessionRegistry());

        List strategies = new LinkedList();
        strategies.add(sessionFixationProtectionStrategy);
        strategies.add(registerSessionAuthenticationStrategy);

        CompositeSessionAuthenticationStrategy compositeSessionAuthenticationStrategy = new CompositeSessionAuthenticationStrategy(strategies);

        return compositeSessionAuthenticationStrategy;
    }
    @Bean
    public SessionRegistry sessionRegistry() {
        SessionRegistry sessionRegistry = new SessionRegistryImpl();
        return sessionRegistry;
    }



}