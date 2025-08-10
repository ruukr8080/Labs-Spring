# CSRF
> Cross-Site Request Forgery
 
 요청을 위조하여 사용자가 원하지 않아도 서버측으로 특정 요청을 강제로 보내는 방식.
 _eg) 내가 한적 없는 내정보 변경_
 

`SecurityFilterChain`에서는 `http.csrf().disable()` 설정을 하지 않으면 `enable()`이 기본으로 적용된다.  
그리고 `http.csrf().enable()` 상태일 때 `CsrfFilter`는 모든 http 요청에 대한 토큰 검증을 진행한다.  
**배포된 서버는 `http.csrf().enable()` 상태여야한다.** 

## http.csrf().enable() 상태에서 http 요청 보내기.

### yml 설정.
> 요청을 보내는 서버 환경에 따라 다름.  
> `expose-request-attributes` <- 대충 이런 키워드이고 `true`로 설정해준다.


### Post 요청 시.

<details><summary>Code</summary>
form submit 방식.

> form 태그로 요청을 보낼 시 `csrf.token`을 포함해야한다.

```html
<form action="/login" method="post" name="loginForm">
    <input type="text" name="username" placeholder="아이디"/>
    <input type="password" name="password" placeholder="비밀번호"/>
    <input type="hidden" name="_csrf" value="{{_csrf.token}}"/>
    <input type="submit" value="로그인"/>
</form>
```
ajax 방식.
> html의 `<head>` 에 `csrf.token` `<meta>` 정보 작성. 

```html
<meta name="_csrf" content="{{_csrf.token}}"/>
<meta name="_csrf_header" content="{{_csrf.headerName}}"/>
```

</details>


## 반전 결말.
사실 세션 방식을 사용하지 않고 jwt 등의 인증 방식을 사용하게 된다면 `csrf.disable()` 상태로 배포서비스 해도 된다.  
따라서 CSRF 방어 로직이 필요없는 경우가 대다수라고 한다.