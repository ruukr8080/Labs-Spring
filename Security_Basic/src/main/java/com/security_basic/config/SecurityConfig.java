package com.security_basic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

   @Bean
   public BCryptPasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   @Bean
   SecurityFilterChain filter(HttpSecurity http) throws Exception{
      http
              .csrf(AbstractHttpConfigurer::disable);
      http
              .authorizeHttpRequests((auth) -> auth
                      .requestMatchers("/", "/join", "/login", "/loginProc", "/joinProc").permitAll()
                      .requestMatchers("/admin/**").hasRole("ADMIN")
                      .requestMatchers("/myPage/**").hasAnyRole("USER", "ADMIN")
                      .anyRequest().authenticated()
              );
      http
              .formLogin((auth) -> auth.loginPage("/login")
                      .loginProcessingUrl("/loginProc").permitAll()
              );
//      http
//              .httpBasic(Customizer.withDefaults());
      http
              .sessionManagement((session) -> session
                      .maximumSessions(1) // 하나의 아이디에서 다중으로 로그인을 허용하는 갯수.
                      .maxSessionsPreventsLogin(true)); //`maximumSessions`에 파라미터로 설정한 값을 초과시(true) ? 새로운 로그인 차단 : 기존 세션 하나 삭제;
      http
              .sessionManagement((session) -> session
                      .sessionFixation().none() // none() : 로그인시 세션정보 변경 안함-*취약* | newSession() : 로그인시 세션 새로 생성 | changeSessionId() : 로그인시 동일한 세션에 대한 id 변경.
              ); //none

      return http.build();
   }
   @Bean
   public UserDetailsService userDetailsService() {
      UserDetails inMemoryAdmin = User.builder()
              .username("inMemoryAdmin")
              .password("0000")
              .roles( "ADMIN")
              .build();
      UserDetails inMemoryUser = User.builder()
              .username("inMemoryUser")
              .password("789456")
              .roles( "USER")
              .build();
      return new InMemoryUserDetailsManager(inMemoryAdmin,inMemoryUser);
   }


}