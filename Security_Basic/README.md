`java 21` |  `spring 3.4.7`
<details><summary> dependencies </summary>

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
```

</details>
<details><summary> Http request 소스 </summary>

```txt
POST http://localhost:8080/joinProc
Content-Type: application/json

{
"username"="admin",
"password"="1234"
}


###
POST http://localhost:8080/loginProc
Content-Type: application/json

{
"username"="admin",
"password"="1234"
}

###
```

</details>

---

## 목표
#### Filter :
> Spring-Security의 핵심 개념 이해.
#### Security-Basic :
> { 인증 / 인가 / 저장 / 중복 검증 } 구현.
#### Session : 
> 세션 소멸 시간, 다중 로그인 통제(아이디당 세션 생성 갯수)
#### CSRF :
> CSRF 개념 이해와 사용법
#### HttpBasic :
> `HttpBasic` 인증 방식의 이해와 활용.
#### InMemoryUser 생성,관리 : (redis 아님)
> `InMemoryUserDetailsManager`로 InMemoryUser 등록
 
