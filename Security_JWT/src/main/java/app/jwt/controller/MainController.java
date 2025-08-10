package app.jwt.controller;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@ResponseBody
public class MainController {

    @GetMapping("/")
    public Map<String, Object> mainP() {
        Map<String, Object> data = new HashMap<>();
        Object id = SecurityContextHolder.getContext().getAuthentication().getName();
        Object test = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        Object role = auth.getAuthority();

        data.put("toString 으로 가져온 role", test);
        data.put("role", role);
        data.put("id", id);
        return data;
    }
}
