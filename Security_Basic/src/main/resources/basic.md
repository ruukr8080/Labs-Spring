
### 1. User Entity,UserRole 생성
<details><summary>Code</summary>

```java
@Getter
@Setter
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    UserRole role;

}
```
```java
@Getter
@RequiredArgsConstructor
public enum UserRole {
    ROLE_ADMIN,
    ROLE_USER
}
```


</details>

### 2. JoinDto 생성
<details><summary>Code</summary>

</details>

### 3. UserRepository 생성
<details> <summary> Code </summary>

```java
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByUsername(String username);

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
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/login", "/join").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated());
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
}
```
</details>

### 5. JoinService 생성
<details> <summary> Code </summary>

```java
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
```

</details>

### 6. JoinController 생성

<details><summary>Code</summary>

```java
@Controller
@ResponseBody
public class JoinController {

    private final JoinService joinService;

    public JoinController(JoinService joinService){
        this.joinService = joinService;
    }

    @PostMapping("/join")
    public String joinProcess(JoinDto joinDto) {
        joinService.joinProcess(joinDto);
        return "oK";
    }
}
```

</details>
