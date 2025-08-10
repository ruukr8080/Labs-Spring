package app.jwt.service;

import app.jwt.dto.JoinDto;
import app.jwt.entity.User;
import app.jwt.repo.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static app.jwt.entity.UserRole.ROLE_ADMIN;

@Service
public class JoinService {

   private final UserRepository userRepository;
   private final BCryptPasswordEncoder encoder;

   public JoinService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
      this.userRepository = userRepository; //초기화
      this.encoder = encoder;
   }

   public void joinProcess(JoinDto joinDto) {
      String username = joinDto.getUsername();
      String password = joinDto.getPassword();
      Boolean isExist = userRepository.existsByUsername(username);

      if (isExist) {
         return;
      }
      User data = new User();

      data.setUsername(username);
      data.setPassword(encoder.encode(password));
      data.setRole(ROLE_ADMIN);

      userRepository.save(data);
   }
}
