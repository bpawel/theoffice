package com.theoffice.controllers;


import com.theoffice.dto.AccountDTO;
import com.theoffice.dto.NewPasswordDTO;
import com.theoffice.services.authentication.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/login")
public class LoginController {
  private AuthenticationService authenticationService;

  @Autowired
  public LoginController(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER') OR hasRole('EMPLOYEE')")
  @GetMapping
  public AccountDTO getUserDetails() {
    return authenticationService.getDetails();
  }

  @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER') OR hasRole('EMPLOYEE')")
  @PutMapping(value = "/new")
  public void setNewPassword(@RequestBody NewPasswordDTO newPasswordDTO) {
    this.authenticationService.setNewPassword(newPasswordDTO);
  }
}
