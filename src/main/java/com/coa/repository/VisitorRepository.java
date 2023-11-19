package com.coa.repository;

import com.coa.model.Visitor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor,Long> {

    Page<Visitor> findByNameContainingIgnoreCase(String name, Pageable pageable);


    @Query("Select v from Visitor v where v.name = :name")
    Optional<Visitor> findVisitorByName(@Param("name")String name);
}
