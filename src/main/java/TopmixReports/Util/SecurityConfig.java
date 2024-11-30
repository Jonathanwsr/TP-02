package TopmixReports.Util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.FormLoginDsl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration

public class SecurityConfig {

    @Bean

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .dispatcherTypeMatchers(HttpMethod.valueOf("/relatorios/**")).permitAll()
                .anyRequest().authenticated();
    }


    protected void libera(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .dispatcherTypeMatchers(HttpMethod.valueOf("/acumulados/**")).permitAll()
                .anyRequest().authenticated();

    }

    }


