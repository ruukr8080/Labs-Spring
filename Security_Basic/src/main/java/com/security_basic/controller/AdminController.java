package com.security_basic.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AdminController {

   @GetMapping("/admin")
   public String adminApi() {

      return "admin";
   }


}
