

### 1. application.yml 작성
<details><summary>Code</summary>

```java
ser
```

</details>

### 2. JoinDto 생성
<details><summary>Code</summary>

```java
@Setter
@Getter
public class JoinDto {

   private String username;
   private String password;
}
```

</details>

### 3. UserRepository 생성
<details> <summary> Code </summary>

```java
public interface UserRepository extends JpaRepository<User, Long> {

   boolean existsUserByUsername(String username);
   User findUserByUsername(String username);
}
```
</details>

### 4. SecurityConfig 생성
<details><summary>Code</summary>

```java
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
                      .anyRequest().authenticated()
              );
      http
              .formLogin((auth) -> auth.loginPage("/login")
                      .loginProcessingUrl("/loginProc").permitAll()
              );
      return http.build();
   }
}
```
</details>

### 5. CustomUserDetailsService 생성
<details> <summary> Code </summary>

```java
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
```
</details>


### 6. CustomUserDetails 생성
<details> <summary> Code </summary>

```java
public class CustomUserDetails implements UserDetails {

   private final User user;

   public CustomUserDetails(User user) {
      this.user = user;
   }

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      Collection<GrantedAuthority> grant = new ArrayList<>();
      grant.add(new GrantedAuthority() {
         @Override
         public String getAuthority() {
            return user.getRole();
         }
      });
      return grant;
   }

   @Override
   public String getPassword() {
      return user.getPassword();
   }

   @Override
   public String getUsername() {
      return user.getUsername();
   }

   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   @Override
   public boolean isAccountNonLocked() {
      return true;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return true;
   }

   @Override
   public boolean isEnabled() {
      return true;
   }
}

```

</details>


### 7. JoinService 생성
<details> <summary> Code </summary>

```java

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
```

</details>

### 8. JoinController 생성

<details><summary>Code</summary>

```java
@Slf4j
@RestController
@RequiredArgsConstructor
public class JoinController {

   private final JoinService joinService;

   @PostMapping("/joinProc")
   public String joinProc(@RequestBody JoinDto joinDto) {
      log.info("request : {} | {} ", joinDto.getUsername(),joinDto.getPassword());
      joinService.joinProc(joinDto);
      return "good";
   }
}
```

</details>
