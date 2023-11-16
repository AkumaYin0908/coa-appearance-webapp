package com.coa.repository;

import com.coa.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    @Query("select role from Role role where role.name= :roleName")
    Role findByRoleName(@Param("roleName") String roleName);
}
