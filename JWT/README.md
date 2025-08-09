# 실습 환경 만들기

## 프로젝트 생성.
`java 21` |  `spring 3.4.7`  
<details><summary> build.gradle의 dependencies 주입 </summary>

```txt
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter'
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-actuator'
    testImplementation 'org.springframework.security:spring-security-test'
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    runtimeOnly 'com.mysql:mysql-connector-j'
    testRuntimeOnly 'org.junit.platform:junit-platform-launcher'

    implementation("io.jsonwebtoken:jjwt-api:0.12.6")
    implementation("io.jsonwebtoken:jjwt-impl:0.12.6")
    implementation("io.jsonwebtoken:jjwt-jackson:0.12.6")
}
```

</details>
<details><summary> application.yml 설정 </summary>

```txt
server:
  port: 8080
logging:
  level:
    root: WARN
    org.hibernate.orm: ERROR

spring:
  datasource:
    url: jdbc:mysql://:3306/jwt?serverTimezone=Asia/Seoul&characterEncoding=UTF-8
    username: test
    password: sa
  jpa:
    database-platform: org.hibernate.dialect.MySQLDialect
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        format_sql: true
    show-sql: true
    open-in-view: false
  jwt:
    secret: "jjjjjjjjjjjjwwwwwwwwwwwwwwtttttttttttttttjjjjjjjjjjjjwwwwwwwwwwwwwwtttttttttttttttjjjjjjjjjjjjwwwwwwwwwwwwwwtttttttttttttttjjjjjjjjjjjjwwwwwwwwwwwwwwtttttttttttttttjjjjjjjjjjjjwwwwwwwwwwwwwwttttttttttttttt"
    expiration: 3600
```

</details>
<details><summary> Http request 소스 </summary>

```txt
POST http://localhost:8080/join
Content-Type: application/x-www-form-urlencoded

username=admin &
password=1234

###
POST http://localhost:8080/login
Content-Type: application/x-www-form-urlencoded

username=admin &
password=1234

###
```

</details>

---

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

</dtails>

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



---






--- 

## JWT 기반 로그인 기능 구현

- 아이디,비밀번호 검증을 위한 `LoginFilter` 작성
- DB에 저장된 회원 정보를 기반으로 검증하는 로직 작성
- 로그인 성공시 JWT를 반환할 `JWTSuccessHandler` 생성
- `CustomFilter`를 `SecurityConfig`에 등록

### 1. 로그인 요청 받기 : LoginFilter 작성 -> SecurityConfig에 Filter 등록.
> 로그인 검증을 위한 커스텀 필터 작성.  
> 그리고 필터를 작성했으면 SecurityConfig에 등록해야한다.
>     UsernamePasswordAuthenticationFilter 구현.   

<details><summary> 1.LoginFilter 작성</summary>

```java
@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public LoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        String username = obtainUsername(req);
        String pw = obtainPassword(req);
        log.info("username : {}, pw : {}", username, pw);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, pw, null);

        return authenticationManager.authenticate(authToken);
    }

    @Override
    public void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain filter, Authentication auth) {


    }

    @Override
    public void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse res, AuthenticationException authenticationException) {

    }


}

```

</details>

<details><summary> 2. `SecurityConfig`에 `LoginFilter`등록 </summary>

**SecurityConfig Code**

```Java

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authConfig;

    public SecurityConfig(AuthenticationConfiguration authConfig) {
        this.authConfig = authConfig;
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
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
        http
                .addFilterAt(new LoginFilter(authenticationManager(authConfig)), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
```

- filter 등록 방법 : `SecurityFilterChain` `@Bean`에 `addFilterAt`을 사용하여 등록한다.  

> addFilterBefore,After이 있는데, 기존의 필터를 `CustomFilter`로 대체 등록 할거기 때문에 `addFilterAt`를 사용한다.    
> 첫번째 parameter는 교체할 필터 : `new LoginFilter()`. 두번째 parameter는 교체될 기존 필터 : `UsernamePasswordAuthenticationFilter.class`  

- 스프링 시큐리티에서 제공하는 Bean을 어느 클래스에서 생성자 주입을 했다면,  `SecurityConfig`에서도 Bean을 명시해줘야 스프링 컨텍스트가 찾을 수 있다.  
이 경우엔 `LoginFilter`에서 `AuthenticationManager`을 생성자주입으로 쓰겠다 작성했으니,`SecurityConfig`에서도 마찬가지로 `AuthenticationManager`@Bean을 등록해준다.

```md
- AutehnticationManager : 검증 담당관. DB의 유저정보를 조회하여 `UserDetailsService`를 이용하여 검증.
- UsernamePasswordAuthenticationToken : username,password,role을 parameter로 받아오는 토큰이자 버킷
```

</details>
