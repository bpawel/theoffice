package com.theoffice.dto;

import com.theoffice.entities.Account;
import com.theoffice.entities.AccountDetails;
import com.theoffice.entities.Department;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class AccountDTO {

    @NotNull
    @Min(1)
    private Long id;

    @NotNull
    @Email
    @Length(min = 3, max = 50)
    private String email;

    @NotNull
    @Size(min = 1)
    @Length(min = 1, max = 50)
    private String firstName;

    @NotNull
    @Size(min = 1)
    @Length(min = 1, max = 50)
    private String secondName;

    @NotNull
    @Min(18)
    @Max(67)
    private Integer age;

    @NotNull
    private boolean suspended;

    private Integer workedHours;

    private Double salary;

    @NotNull
    private boolean isSalaryThresholdExceeded;

    @NotNull
    private boolean isDefaultPassword;

    private String department;

    private Double departmentHourlyRate;

    private String role;

    public static AccountDTO convert(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        AccountDetails accountDetails = account.getAccountDetails();
        accountDTO.setEmail(accountDetails.getEmail());
        accountDTO.setFirstName(accountDetails.getFirstName());
        accountDTO.setSecondName(accountDetails.getSecondName());
        accountDTO.setAge(accountDetails.getAge());
        accountDTO.setWorkedHours(accountDetails.getWorkedHours());
        accountDTO.setSalary(accountDetails.getSalary());
        accountDTO.setSuspended(account.isSuspended());
        accountDTO.setSalaryThresholdExceeded(accountDetails.isSalaryThresholdExceeded());
        accountDTO.setDefaultPassword(account.isDefaultPassword());
        Optional<Department> department;
        department = Optional.ofNullable(accountDetails.getDepartment());
        department.ifPresent(d -> {
            accountDTO.setDepartment(department.get().getName());
            accountDTO.setDepartmentHourlyRate(department.get().getHourlyRate());
        });
        accountDTO.setRole(account.getRole());
        return accountDTO;
    }

    public static Account convert(AccountDTO accountDTO) {
        Account account = new Account();
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setFirstName(accountDTO.getFirstName());
        accountDetails.setSecondName(accountDTO.getSecondName());
        accountDetails.setAge(accountDTO.getAge());
        accountDetails.setEmail(accountDTO.getEmail());
        account.setAccountDetails(accountDetails);
        return account;
    }
}
