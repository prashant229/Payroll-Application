package com.payroll.gateway_server.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfigClass {

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity httpSecurity){

        httpSecurity.authorizeExchange(authorizeExchangeSpec ->
                authorizeExchangeSpec
                        .pathMatchers(HttpMethod.GET).permitAll()
                        .pathMatchers("/payroll/leaves/**").authenticated()
                        .pathMatchers("/payroll/employee/**").authenticated()
                        .pathMatchers("/payroll/salary/**").authenticated()

        ).oauth2ResourceServer(oAuth2ResourceServerSpec ->
                oAuth2ResourceServerSpec.jwt(Customizer.withDefaults()));

        httpSecurity.csrf(csrfSpec -> csrfSpec.disable());

        return httpSecurity.build();
    }
}
