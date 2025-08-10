package app.jwt.jwt;


import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
        log.info("요청 username : {}, pw : {}", username, pw);

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
