package com.theoffice.services.authentication;

import com.theoffice.dto.AccountDTO;
import com.theoffice.dto.NewPasswordDTO;
import com.theoffice.entities.Account;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public interface AuthenticationService extends UserDetailsService {

  UserDetails loadUsersByUsername(String username) throws UsernameNotFoundException;

  AccountDTO getDetails();

  Account getCurrent();

  void setNewPassword(NewPasswordDTO newPasswordDTO);
}
