package com.coa.repository;

import com.coa.constants.query_contants.AppearanceConstant;
import com.coa.model.Appearance;
import com.coa.model.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppearanceRepository extends JpaRepository<Appearance,Long> {

    @Query(value = AppearanceConstant.findByVisitor,nativeQuery = true)
    List<Appearance> findByVisitor(Long id);

    @Query(value = AppearanceConstant.findByVisitorName,nativeQuery = true)
    List<Appearance> findByVisitorName(String name);

    @Query(value = AppearanceConstant.findByVisitorAndDateIssued,nativeQuery = true)
    List<Appearance> findByVisitorAndDateIssued(Long id, LocalDate dateIssued);

//    @Query(value = AppearanceConstant.findByPurpose,nativeQuery = true)
//    List<Appearance> findByPurpose(String description);

    @Query(value = AppearanceConstant.findByDateIssued,nativeQuery = true)
    List<Appearance> findByDateIssued(LocalDate dateIssued);

    @Query(value = AppearanceConstant.findByMonth, nativeQuery = true)
    List<Appearance> findByMonth(Integer month);

    @Query(value = AppearanceConstant.findByYear,nativeQuery = true)
    List<Appearance> findByYear(Integer year);

    @Query(value = AppearanceConstant.findByReference, nativeQuery = true)
    Optional<Appearance> findByReference(String reference);
}
