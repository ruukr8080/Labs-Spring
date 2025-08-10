package com.security_basic.controller;

import com.security_basic.dto.JoinDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoginController {

   String uri = "localhost:8080/login";

   @GetMapping("/login")
   public void loginPage(Model model) {
      model.addAttribute("uri", uri);
      log.info("loginPage!!");
   }


   @PostMapping("/loginProc")
   public String loginApi(@RequestBody JoinDto request) {

      return "ddd";
   }
}
