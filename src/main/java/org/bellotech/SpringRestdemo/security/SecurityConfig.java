package org.bellotech.SpringRestdemo.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private RSAKey rsaKeys;

    @Bean
    public JWKSource<SecurityContext> jwkSource(){

        rsaKeys = Jwks.generateRsa();
        JWKSet jwkSet = new JWKSet(rsaKeys);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
        
    }

    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

 

/*     @Bean
    public InMemoryUserDetailsManager users() {
        return new InMemoryUserDetailsManager(
                User.withUsername("chaand")
                        .password("{noop}password")
                        .authorities("read")
                        .build());
    } */

    @Bean
    public AuthenticationManager authManager(UserDetailsService userDetailsService) {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }


    @Bean
    JwtDecoder jwtDecoder() throws JOSEException{
        return NimbusJwtDecoder.withPublicKey(rsaKeys.toRSAPublicKey()).build();
    }

    @Bean
    JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwks) {

        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http    

        .csrf(csrf -> csrf.ignoringRequestMatchers("/db-console/**"))
        .headers().frameOptions().sameOrigin()
        .and()
        // we are protecting /api/** endpoints with the oAuth2 resource server
                .authorizeHttpRequests()
                .requestMatchers("/token").permitAll()
                .requestMatchers("/**").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/test").authenticated()
                .and()
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                // TODO: remove these after upgrading the DB from H2 infile DB
                http
                .csrf((csrf) -> csrf
                .disable());
                http
                .headers((headers) -> headers
                .frameOptions().disable());


                /* http.csrf().disable();
                http.headers().frameOptions().disable(); */

        return http.build();
    }

}
