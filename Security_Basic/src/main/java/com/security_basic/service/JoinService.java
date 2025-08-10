package com.security_basic.service;

import com.security_basic.dto.JoinDto;
import com.security_basic.entity.User;
import com.security_basic.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JoinService {

   @Autowired
   private UserRepository userRepository;

   @Autowired
   private BCryptPasswordEncoder passwordEncoder;

   public void joinProc(JoinDto joinDto) {

      boolean isExist = userRepository.existsUserByUsername(joinDto.getUsername());
      if(isExist) {
         log.info("중복임!!! {} ", joinDto.getUsername());
         return;

      }
      User data = new User();
      data.setUsername(joinDto.getUsername());
      data.setPassword(passwordEncoder.encode(joinDto.getPassword()));
      data.setRole("ROLE_ADMIN");

      userRepository.save(data);
      log.info("저장 됨{} ", data);
   }
}
