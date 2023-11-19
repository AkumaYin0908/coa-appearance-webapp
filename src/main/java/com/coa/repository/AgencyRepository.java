package com.coa.repository;

import com.coa.model.Agency;
import com.coa.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgencyRepository extends JpaRepository<Agency, Long> {

    @Query("SELECT a FROM Agency a where a.name= :name")
    Optional<Agency> findAgencyName(@Param("name")String name);
}
