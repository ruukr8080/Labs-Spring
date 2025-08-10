package com.security_basic.controller;

import com.security_basic.dto.JoinDto;
import com.security_basic.service.JoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
public class JoinController {

   private final JoinService joinService;

   @PostMapping("/joinProc")
   public String joinProc(@RequestBody JoinDto joinDto) {
      log.info("joinDto: {} | {} ", joinDto.getUsername(),joinDto.getPassword());
      joinService.joinProc(joinDto);
      return "good";
   }
}
