package app.jwt.service;

import app.jwt.dto.CustomUserDetails;
import app.jwt.entity.User;
import app.jwt.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

   @Autowired
   private UserRepository userRepository;

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User data = userRepository.findByUsername(username);
      if (data != null) {
         return new CustomUserDetails(data);
      }
      return null;
   }
}
