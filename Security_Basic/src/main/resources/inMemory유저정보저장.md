# inMemory 유저 정보 저장

> 소수의 유저 데이터를 저장하는 좋은 방법!

굳이 데이터베이스라는 자원을 투자할 필요가 없는 경우는 회원가입 없이 
inMemory 방식으로 유저를 저장한다.

- 처음 설정한 유저 정보로만 로그인을 진행해야한다.
- 유저 정보를 추가하거나 삭제하는 등의 DB 편집 X


---
### `inMenoryUserDetailsManager` 구현. 
[시큐리티 공홈](https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/in-memory.html)

<details><summary>Code</summary>
공홈 코드

```java
@Bean
public UserDetailsService users() {
   UserDetails user = User.builder()
           .username("user")
           .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
           .roles("USER")
           .build();
   UserDetails admin = User.builder()
           .username("admin")
           .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
           .roles("USER", "ADMIN")
           .build();
   return new InMemoryUserDetailsManager(user, admin);
}
```
`SecurityConfig`에 `@Bean` 등록.
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
   
   //... filter , bcryptpassword , ...
   
   @Bean
   public UserDetailsService userDetailsService() {
      UserDetails inMemoryAdmin = User.builder()
              .username("inMemoryAdmin")
              .password("0000")
              .roles("ADMIN")
              .build();
      UserDetails inMemoryUser = User.builder()
              .username("inMemoryUser")
              .password("789456")
              .roles("USER")
              .build();
      return new InMemoryUserDetailsManager(inMemoryAdmin, inMemoryUser);
   }
}
```
</details>

#### 요청 소스

```http request
POST http://localhost:8080/joinProc
Content-Type: application/json

{
"username":"inMemoryAdmin",
"password":"0000"
}

###
POST http://localhost:8080/joinProc
Content-Type: application/json

{
"username":"inMemoryUser",
"password":"789456"
}

###
```

동일한 타입의 `@Bean`이 여러개 등록하면 스프링이 어떤 Bean을 사용할지 몰라 무한루프 오류 가능성이 있다.  
만약 'DB 조회하는 방식'과 '인메모리 조회 방식' 둘 다 사용하려면 그냥 Redis DB를 쓰는게 낫다고 한다.