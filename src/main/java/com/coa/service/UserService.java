package com.coa.service;

import com.coa.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {


    Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<User> findAll(Pageable pageable);

    Optional<User> findById(Long userId);

    Optional<User> findByUserName(String userName);

    void save(User user);

    void deleteById(Long userId);

    void updateActiveStatus(Long userId, boolean active);


}
