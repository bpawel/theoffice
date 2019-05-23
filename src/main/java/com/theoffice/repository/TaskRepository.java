package com.theoffice.repository;

import com.theoffice.entities.Account;
import com.theoffice.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByAccount(Account account);

//    List<Task> findByAccountAndDateBetweenOrderByDateASC(Account account, LocalDate localDate1, LocalDate localDate2);
}
