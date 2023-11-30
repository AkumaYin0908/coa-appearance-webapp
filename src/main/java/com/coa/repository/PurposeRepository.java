package com.coa.repository;

import com.coa.model.Purpose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurposeRepository extends JpaRepository<Purpose,Long> {

    @Query("SELECT p FROM Purpose p WHERE p.purpose = :purpose")
    Optional<Purpose> findByPurpose(@Param("purpose") String purpose);

}
