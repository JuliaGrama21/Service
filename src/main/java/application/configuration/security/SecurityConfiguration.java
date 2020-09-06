package application.configuration.security;

import application.service.impls.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.servlet.http.HttpServletResponse;
import java.security.SecureRandom;

import static application.model.security.Authority.AuthorityType.ADMIN;

@EnableWebSecurity
@PropertySource(value = { "classpath:metadata.properties" })
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${security.passkey.secret}")
    private CharSequence securityKey;

    @Value("${security.passkey.strength}")
    private int strength;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(strength, new SecureRandom(Hex.decode(securityKey)));
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(BCryptPasswordEncoder passwordEncoder, UserDetailsServiceImpl userDetailsService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);

        return daoAuthenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/logout", "/users", "/personal_cabinet", "/myPayments", "/createPayTemplate", "/pay_template", "/template", "/createTemplate", "/createPayment", "/communal_organization", "payment_page").authenticated()
            .antMatchers("/admin_page", "payments", "/logout").hasAuthority(ADMIN.name())
            .antMatchers("/registration", "/login").anonymous()
            .antMatchers("/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login")
            .successHandler(authenticationSuccessHandler)
            .and()
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
            .csrf().disable();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public static ServletListenerRegistrationBean httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            boolean admin = false;

            for (GrantedAuthority auth : authentication.getAuthorities()) {
                if (ADMIN.name().equals(auth.getAuthority())) {
                    admin = true;
                }
            }

            response.setStatus(HttpServletResponse.SC_OK);

            if (admin) {
                response.sendRedirect("/admin_page");
            } else {
                response.sendRedirect("/personal_cabinet");
            }
        };
    }

}