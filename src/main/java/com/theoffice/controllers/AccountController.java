package com.theoffice.controllers;

import com.theoffice.dto.AccountDTO;
import com.theoffice.services.accounts.AccountService;
import com.theoffice.services.accounts.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/accounts")
public class AccountController {

  private AccountService accountService;

  @Autowired
  public AccountController(AccountServiceImpl accountService) {
    this.accountService = accountService;
  }

  @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
  @GetMapping
  public List<AccountDTO> getAll() {
    return accountService.getAll().stream().map(AccountDTO::convert).collect(Collectors.toList());
  }

  @PreAuthorize("hasRole('MANAGER')")
  @GetMapping(value = "/{id}")
  public AccountDTO getById(@PathVariable Long id) {
    return AccountDTO.convert(this.accountService.getById(id));
  }

  @PreAuthorize("hasRole('MANAGER')")
  @GetMapping(value = "/treshhold")
  public List<AccountDTO> getThresholdExceeders() {
    return accountService.getThresholdExceeders().stream().map(AccountDTO::convert).collect(Collectors.toList());
  }

  @PreAuthorize("hasRole('MANAGER')")
  @GetMapping(value = "/department/{department}")
  public List<AccountDTO> getByDepartment(@PathVariable String department) {
    return accountService.getByDepartment(department).stream().map(AccountDTO::convert).collect(Collectors.toList());
  }

  @PreAuthorize("hasRole('EMPLOYEE')")
  @PutMapping
  public void update(@RequestBody @Valid AccountDTO accountDTO) {
    this.accountService.update(AccountDTO.convert(accountDTO));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping(value = "/{id}/suspend")
  public void suspend(@PathVariable Long id) {
    this.accountService.suspendAccount(id);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping(value = "/{id}/unsuspend")
  public void unSuspend(@PathVariable Long id) {
    this.accountService.unSuspendAccount(id);
  }

  @PreAuthorize("hasRole('MANAGER')")
  @GetMapping(value = "/{id}/department/{department}")
  public void setDepartment(@PathVariable Long id, @PathVariable String department) {
    this.accountService.setDepartment(id, department);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping(value = "/{id}")
  public void delete(@PathVariable Long id) {
    this.accountService.delete(id);
  }
}
