package peaksoft.springbootsecuritypractice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import peaksoft.springbootsecuritypractice.service.UserDetailsServiceImpl;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin().permitAll()
                .and()
                .authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/api/user/").hasAuthority("ADMIN")
//                .antMatchers(HttpMethod.GET, "/api/user/").hasAnyAuthority("USER","ADMIN")
//                .antMatchers(HttpMethod.PUT, "/api/user/**").hasAuthority("ADMIN")
//                .antMatchers(HttpMethod.DELETE, "/api/user/**").hasAuthority("ADMIN")
//                .antMatchers(HttpMethod.GET,"/api/user/**").hasAnyAuthority("ADMIN", "USER")
                .and()
                .csrf().disable()
                .httpBasic();
    }
}
