package com.coa.repository;

import com.coa.model.Appearance;
import com.coa.model.Visitor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppearanceRepository extends JpaRepository<Appearance,Long> {


    @Query("SELECT a FROM Appearance a WHERE a.visitor.name = :name")
    Page<Appearance> findByVisitorNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);


    @Query("SELECT a FROM Appearance a WHERE a.visitor = :visitor")
    List<Appearance> listAppearanceByVisitor(@Param("visitor") Visitor visitor);
}
