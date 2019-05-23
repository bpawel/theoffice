package com.theoffice;

import com.theoffice.entities.Account;
import com.theoffice.entities.AccountDetails;
import com.theoffice.entities.Department;
import com.theoffice.entities.Task;
import com.theoffice.repository.AccountRepository;
import com.theoffice.repository.DepartmentRepository;
import com.theoffice.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

@Component
public class DbSeeder implements CommandLineRunner {
    private AccountRepository accountRepository;
    private DepartmentRepository departmentRepository;
    private TaskRepository taskRepository;

    @Autowired
    public DbSeeder(AccountRepository accountRepository, DepartmentRepository departmentRepository, TaskRepository taskRepository) {
        this.accountRepository = accountRepository;
        this.departmentRepository = departmentRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public void run(String... strings) throws Exception {

        Department adminDepartment = new Department("administrator", "administrator", 4000d, 10000d, 25d);
        adminDepartment.setNumberOfEmployeesAssigned(1);
        Department managersDepartment = new Department("managers", "managers", 8000d, 15000d, 35d);
        managersDepartment.setNumberOfEmployeesAssigned(1);
        Department marketingDepartment = new Department("marketing", "marketing", 2000d, 5000d, 20d);
        marketingDepartment.setNumberOfEmployeesAssigned(1);

        departmentRepository.save(adminDepartment);
        departmentRepository.save(managersDepartment);
        departmentRepository.save(marketingDepartment);

        AccountDetails adminDetails = new AccountDetails("admin@admin.com", "Jan", "Kowalski", 38, 148, 3700D);
        adminDetails.setDepartment(adminDepartment);
        Account admin = new Account(1L, "admin", new BCryptPasswordEncoder().encode("admin"), false, false, "ROLE_ADMIN", adminDetails);

        AccountDetails managerDetails = new AccountDetails("manager@manager.com", "Zbigniew", "Nowak", 37, 33, 1155D);
        managerDetails.setDepartment(managersDepartment);
        Account manager = new Account(2L, "manager", new BCryptPasswordEncoder().encode("manager"), false, false, "ROLE_MANAGER", managerDetails);

        AccountDetails employeeDetails = new AccountDetails("employee@employee.com", "Kamil", "Nowacki", 39, 59, 1180D);
        employeeDetails.setDepartment(marketingDepartment);
        Account employee = new Account(3L, "employee", new BCryptPasswordEncoder().encode("employee"), false, false, "ROLE_EMPLOYEE", employeeDetails);

        AccountDetails employee2Details = new AccountDetails("employee2@employee2.com", "Kamil2", "Nowacki2", 39, 59, 1180D);
        employee2Details.setDepartment(marketingDepartment);
        Account employee2 = new Account(4L, "employee2", new BCryptPasswordEncoder().encode("employee2"), false, true, "ROLE_EMPLOYEE", employee2Details);

        accountRepository.save(admin);
        accountRepository.save(manager);
        accountRepository.save(employee);
        accountRepository.save(employee2);


        Task task1 = new Task("test1", "test1", LocalDate.now(), 7, employee, "marketing");
        Task task2 = new Task("test2", "test2", LocalDate.now(), 7, employee, "marketing");
        Task task3 = new Task("test3", "test3", LocalDate.now(), 7, employee, "marketing");
        Task task4 = new Task("test4", "test4", LocalDate.now(), 7, employee, "marketing");

        taskRepository.saveAll(Arrays.asList(task1, task2, task3, task4));
    }
}
