package com.theoffice.repository;

import com.theoffice.entities.Account;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PagingRepository extends PagingAndSortingRepository<Account, Long> {
}
