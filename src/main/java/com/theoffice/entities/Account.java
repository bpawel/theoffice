package com.theoffice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name = "accounts")
public class Account implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "account_id")
  private Long id;

  @Column(name = "username", unique = true)
  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "is_default_password")
  private boolean isDefaultPassword;

  @Column(name = "is_suspended")
  private boolean isSuspended;

  @Column(name = "role")
  private String role;

  @JsonIgnore
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "account_details_id")
  private AccountDetails accountDetails;

  public Account() {
  }

  public Account(Long id, String username, String password, boolean isDefaultPassword, boolean isSuspended, String role, AccountDetails accountDetails) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.isDefaultPassword = isDefaultPassword;
    this.isSuspended = isSuspended;
    this.role = role;
    this.accountDetails = accountDetails;
  }

  public Account(String username, String password, boolean isDefaultPassword, boolean isSuspended, String role, AccountDetails accountDetails) {
    this.username = username;
    this.password = password;
    this.isDefaultPassword = isDefaultPassword;
    this.isSuspended = isSuspended;
    this.role = role;
    this.accountDetails = accountDetails;
  }
}

