package com.security_basic.service;

import com.security_basic.dto.CustomUserDetails;
import com.security_basic.entity.User;
import com.security_basic.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

   @Autowired
   private UserRepository userRepository;

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User userDetails = userRepository.findUserByUsername(username);

      if(userDetails != null) {
         return new CustomUserDetails(userDetails);
      }
      log.info("loadUserByUsername: {} ", userDetails.getUsername());
      return null;
   }
}
