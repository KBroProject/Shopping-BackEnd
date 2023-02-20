package com.web.shopping.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Bean
//    public PasswordEncoder encodePwd() {
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable()
            .authorizeRequests()
//                .antMatchers("/api/members/*").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
//                .loginPage("/login")
////                .loginProcessingUrl()
                .defaultSuccessUrl("/")
                .and()
            .httpBasic(); //postman 테스트용도(Authorization Type = Basic Auth)
//                .failureUrl("/access_denied")
//                .headers()
//                .disable()
//                .httpBasic()
//                .disable()
//                .rememberMe()
//                .disable()
//                .logout()
//                .disable()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//            .exceptionHandling()
//                .accessDeniedHandler(accessDeniedHandler())
//                .authenticationEntryPoint(authenticationEntryPoint())
//                .and()
//            .addFilterBefore(jwtAuthenticationFilter(jwt, tokenService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
