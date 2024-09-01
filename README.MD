# 환경
- JDK 17
- Spring Boot 3.3.3
- gradle
- Security
- Spring Data JPA - H2
- thymeleaf
- intelliJ ultimate

```null
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.thymeleaf.extras:thymeleaf-extras-springsecurity6'
    compileOnly 'org.projectlombok:lombok'
    developmentOnly 'org.springframework.boot:spring-boot-devtools'
    runtimeOnly 'com.h2database:h2'
    annotationProcessor 'org.projectlombok:lombok'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.springframework.security:spring-security-test'
    testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
}
```

### REF
- [개발자유미](https://www.youtube.com/watch?v=y0PXQgrkb90&list=PLJkjrxxiBSFCKD9TRKDYn7IE96K2u3C3U&index=1)

