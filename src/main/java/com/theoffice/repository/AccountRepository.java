package com.theoffice.repository;

import com.theoffice.entities.Account;
import com.theoffice.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUsername(String username);

    Optional<List<Account>> findByAccountDetails_Department(Department department);

    Optional<List<Account>> findByAccountDetails_IsSalaryThresholdExceeded(boolean value);

    Optional<List<Account>> findByRole(String role);
}
