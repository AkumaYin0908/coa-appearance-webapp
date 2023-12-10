package com.coa.repository;

import com.coa.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {


    Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("select user from User user where user.userName= :userName")
    Optional<User> findByUserName(@Param("userName") String userName);


    @Query("UPDATE User u SET u.active = :active WHERE u.userId= :userId")
    @Modifying
    void updateActiveStatus(Long userId, boolean active);


}
