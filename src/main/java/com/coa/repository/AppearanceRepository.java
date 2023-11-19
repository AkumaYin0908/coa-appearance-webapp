package com.coa.repository;

import com.coa.model.Appearance;
import com.coa.model.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppearanceRepository extends JpaRepository<Appearance,Long> {

    @Query("Select a from Appearance a where a.visitor = :id")
    List<Visitor> listAppearanceByVisitor(@Param("id") Long id);
}
