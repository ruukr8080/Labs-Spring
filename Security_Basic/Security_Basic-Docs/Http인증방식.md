# Http 인증 방식
> Spring-Security로 구현할 수 있는 Http 인증 방식은 크게 두가지로 나뉜다.
- formLogin 방식 
  - 특정 http파일(`login.hmlt`)의 내부에 `<from>` 태그로 id와password를 입력하여 서버에 `POST`요청으로 `submit` 하는 방식. 
- httpBasic 방식 
  - ID와 Password를 브라우저에서 입력해서 `Base64`방식으로 인코딩하여 HTTP 인증 헤더에 넣어 서버에 인증요청 보내는 방식  

참고로 둘 다 인증 처리 방식으로 AuthenticationManager를 쓰고, 인증 성공 시 SecurityContextHolder에 인증 정보를 저장하는 점은 같다.  
그리고 요즘은 API 인증에는 위 방식들보단 `OAuth2 + JWT 토큰 기반 인증`을 더 많이 사용한다.
 
### SecurityConfig 작성
> formLogin을 삭제하던가 disable()로 처리 하고,httpBasic을 기본인증 방식으로 설정해준다.

<details><summary>Code</summary>


```java

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Bean
SecurityFilterChain filter(HttpSecurity http) throws Exception {
  http
          .formLogin(AbstractHttpConfigurer::disable);
  http
          .httpBasic(Customizer.withDefaults());
}

```
</details>