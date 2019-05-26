package com.theoffice.services.accounts;

import com.theoffice.entities.Account;

import java.util.List;

public interface AccountService {

  List<Account> getAll();

  Account getById(Long id);

  Account getByUsername(String username);

  List<Account> getByDepartment(String dep);

  void  add(Account account);

  void update(Account account);

  void delete(Long id);

  void suspendAccount(Long id);

  void unSuspendAccount(Long id, String department);

  void setDepartment(long id, String department1);

  List<Account> getThreshholdExceeders();

  void unSuspendAccount(Long id);

  List<Account> getThresholdExceeders();
}
