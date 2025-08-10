package com.security_basic.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
public class MainController {

   @GetMapping("/")
   public Map<String, Object> mainApi() {
      Map<String, Object> map = new HashMap<>();
      String username = SecurityContextHolder.getContext().getAuthentication().getName();
      String role = SecurityContextHolder.getContext().getAuthentication().getName();
      map.put(username, role);

      return map;
   }
}
