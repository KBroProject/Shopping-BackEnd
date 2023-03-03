package com.web.shopping.security;

import com.web.shopping.exception.CustomAccessDeniedHandler;
import com.web.shopping.exception.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable()
            .authorizeRequests()
                .antMatchers("/api/**", "/", "/exception/**").permitAll()
                .anyRequest().authenticated()
            .and()
                .formLogin()
                .defaultSuccessUrl("/")
//                .loginPage("/login")
////                .loginProcessingUrl()
            .and()
            .httpBasic()
                .disable()
            /** 세션 사용하지 않음 **/
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
            .and()
            .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler());


            //                .failureUrl("/access_denied")
//                .headers()
//                .disable()
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
