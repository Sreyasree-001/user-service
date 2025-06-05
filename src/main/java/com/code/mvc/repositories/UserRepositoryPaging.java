package com.code.mvc.repositories;

import com.code.mvc.entities.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserRepositoryPaging extends PagingAndSortingRepository<User, Long> {
    // pagination and sorting capabilities for user data retrieval
    @Query("SELECT u FROM User u")
    List<User> findAll(Sort sort);
}
