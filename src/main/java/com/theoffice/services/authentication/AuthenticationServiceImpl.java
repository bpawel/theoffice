package com.theoffice.services.authentication;

import com.theoffice.dto.AccountDTO;
import com.theoffice.dto.NewPasswordDTO;
import com.theoffice.entities.Account;
import com.theoffice.exceptions.accounts.AccountConflictException;
import com.theoffice.exceptions.accounts.AccountNotFoundException;
import com.theoffice.repository.AccountRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

  private AccountRepository accountRepository;

  public AuthenticationServiceImpl(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }


  @Override
  public UserDetails loadUsersByUsername(String username) throws UsernameNotFoundException {
    Optional<Account> account = accountRepository.findByUsername(username);
    if (account.isPresent()) {
      GrantedAuthority authority;
      if (account.get().isSuspended()) {
        authority = new SimpleGrantedAuthority("ROLE_SUSPENDED");
      } else {
        authority = new SimpleGrantedAuthority(account.get().getRole());
      }
      UserDetails userDetails = (UserDetails) new User(account.get().getUsername(), account.get().getPassword(), Arrays.asList(authority));

      return userDetails;
    } else {
      throw new AccountNotFoundException();
    }
  }


  @Override
  public AccountDTO getDetails() {
    return AccountDTO.convert(getCurrent());
  }

  @Override
  public Account getCurrent() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (!(auth instanceof AnonymousAuthenticationToken)) {
      UserDetails userDetails =
        (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      return accountRepository.findByUsername(userDetails.getUsername()).get();
    } else {
      throw new AccountNotFoundException();
    }
  }

  @Override
  public void setNewPassword(NewPasswordDTO newPasswordDTO) {
    if (newPasswordDTO.getPassword().equals(newPasswordDTO.getPassword2())) {
      Account account = this.getCurrent();
      if (account.isDefaultPassword()) {
        account.setPassword(new BCryptPasswordEncoder().encode(newPasswordDTO.getPassword()));
        account.setDefaultPassword(false);
        this.accountRepository.save(account);
      } else {
        throw new AccountConflictException();
      }
    } else {
      throw new AccountConflictException();
    }
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return null;
  }
}
