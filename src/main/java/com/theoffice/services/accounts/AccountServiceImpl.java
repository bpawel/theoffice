package com.theoffice.services.accounts;

import com.theoffice.entities.Account;
import com.theoffice.entities.Department;
import com.theoffice.exceptions.accounts.AccountNotFoundException;
import com.theoffice.exceptions.departments.DepartmentNotFoundException;
import com.theoffice.repository.AccountRepository;
import com.theoffice.repository.DepartmentRepository;
import com.theoffice.services.authentication.AuthenticationService;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.apache.commons.text.CharacterPredicates.DIGITS;
import static org.apache.commons.text.CharacterPredicates.LETTERS;

@Service
public class AccountServiceImpl implements AccountService {

  private AccountRepository accountRepository;
  private AuthenticationService authenticationService;
  private DepartmentRepository departmentRepository;

  public AccountServiceImpl(AccountRepository accountRepository, AuthenticationService authenticationService, DepartmentRepository departmentRepository) {
    this.accountRepository = accountRepository;
    this.authenticationService = authenticationService;
    this.departmentRepository = departmentRepository;
  }

  @Override
  public List<Account> getAll() { return this.accountRepository.findAll(); }

  @Override
  public Account getById(Long id) {
    Optional<Account> optional = Optional.ofNullable(accountRepository.findById(id).orElse(null));
    if (optional.isPresent()) {
      return  optional.get();
    } else {
      throw new AccountNotFoundException();
    }
}

  public Account getByUsername(String username) {
    Optional<Account> optional = accountRepository.findByUsername(username);
    if (optional.isPresent()) {
      return optional.get();
    } else {
      throw new AccountNotFoundException();
    }
  }

  @Override
  public List<Account> getByDepartment(String dep) {
    Optional<Department> department = departmentRepository.findByName(dep);
    if (department.isPresent()) {
      Optional<List<Account>> accounts = this.accountRepository.findByAccountDetails_Department(department.get());
      if (accounts.isPresent()) return accounts.get();
      else throw new AccountNotFoundException();
    } else {
      throw new DepartmentNotFoundException();
    }
  }

  @Override
  public void add(Account account) {
    String username = account.getAccountDetails().getFirstName().toLowerCase().substring(0, 1) + account.getAccountDetails().getSecondName().toLowerCase();
    account.setUsername(username);
    RandomStringGenerator generatorPassword = new RandomStringGenerator.Builder()
      .withinRange('0', 'z')
      .filteredBy(LETTERS, DIGITS)
      .build();
    String randomPassword = generatorPassword.generate(10);
    this.accountRepository.save(account);
  }

  @Override
  public void update(Account updatedAccount) {
    Account account = this.authenticationService.getCurrent();
    account.getAccountDetails().setEmail(updatedAccount.getAccountDetails().getEmail());
    account.getAccountDetails().setAge(updatedAccount.getAccountDetails().getAge());
    account.getAccountDetails().setFirstName(updatedAccount.getAccountDetails().getFirstName());
    account.getAccountDetails().setSecondName(updatedAccount.getAccountDetails().getSecondName());
    this.accountRepository.save(account);
  }

  @Override
  public void delete(Long id) {
    Optional<Account> optional = Optional.ofNullable(accountRepository.findById(id).orElse(null));
    if (optional.isPresent()) {
      accountRepository.delete(optional.get());
    } else {
      throw new AccountNotFoundException();
    }
  }

  @Override
  public void suspendAccount(Long id) {
    Optional<Account> optional = Optional.ofNullable(accountRepository.findById(id).orElse(null));
    if (optional.isPresent()) {
      optional.get().setSuspended(true);
      accountRepository.save(optional.get());
    } else {
      throw new AccountNotFoundException();
    }
  }

  @Override
  public void unSuspendAccount(Long id, String department) {

  }

  @Override
  public void setDepartment(long id, String department) {
    Optional<Account> account = Optional.ofNullable(this.accountRepository.findById(id).orElse(null));
    if (account.isPresent()) {
      if (department.equals("")) {
        account.get().getAccountDetails().setDepartment(null);
        this.accountRepository.save(account.get());
      } else {
        Optional<Department> newDepartment = this.departmentRepository.findByName(department);
        if (newDepartment.isPresent()) {
          Optional<Department> oldDepartment = Optional.ofNullable(account.get().getAccountDetails().getDepartment());
          if (oldDepartment.isPresent()) {
            oldDepartment.get().setNumberOfEmployeesAssigned(oldDepartment.get().getNumberOfEmployeesAssigned() - 1);
          }
          account.get().getAccountDetails().setDepartment(newDepartment.get());
          newDepartment.get().setNumberOfEmployeesAssigned(newDepartment.get().getNumberOfEmployeesAssigned() + 1);
          this.accountRepository.save(account.get());
        } else {
          throw new DepartmentNotFoundException();
        }
      }
    } else {
      throw new AccountNotFoundException();
    }
  }


  @Override
  public List<Account> getThreshholdExceeders() {
    return null;
  }

  @Override
  public void unSuspendAccount(Long id) {
    Optional<Account> optional = Optional.ofNullable(accountRepository.findById(id).orElse(null));
    if (optional.isPresent()) {
      optional.get().setSuspended(false);
      accountRepository.save(optional.get());
    } else {
      throw new AccountNotFoundException();
    }
  }

  @Override
  public List<Account> getThresholdExceeders() {
    Optional<List<Account>> optional = accountRepository.findByAccountDetails_IsSalaryThresholdExceeded(true);
    if (optional.isPresent()) {
      return optional.get();
    } else {
      throw new AccountNotFoundException();
    }
  }
}

