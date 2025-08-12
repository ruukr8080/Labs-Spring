## 프로젝트 생성.
**RestApi-server**

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

## 목표
### JWT 인증방식 이해 :
`JWT인증방식`에 의한 시큐리티 동작 원리 이해.

### Jwt 기능 구현 실습 : 
DB기반 검증 / filter / handler / session attribute
/ [회원가입 / 로그인(인증) / 인가] / CORS 연동

### 보안 심화 학습 :
보안을 중점으로 한 개발철학 확립

---
