package com.coa.repository;

import com.coa.model.Appearance;
import com.coa.model.Visitor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppearanceRepository extends JpaRepository<Appearance,Long> {


    @Query("SELECT a FROM Appearance a WHERE a.visitor.name = :name")
    Page<Appearance> findByVisitorNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);

    @Query("SELECT a FROM Appearance a WHERE a.purpose.purpose = :purpose AND a.visitor = :visitor")
    Page<Appearance> findByPurposeContainingIgnoreCase(@Param("purpose")String purpose , @Param("visitor")Visitor visitor, Pageable pageable);

    @Query("SELECT a FROM Appearance a WHERE a.purpose.purpose = :purpose AND MONTH(a.dateIssued) = :month AND a.visitor = :visitor")
    Page<Appearance> findByPurposeAndMonthContainingIgnoreCase(@Param("purpose")String purpose,@Param("month") Integer  month, @Param("visitor")Visitor visitor, Pageable pageable);

    @Query("SELECT a FROM Appearance a WHERE a.purpose.purpose = :purpose AND YEAR(a.dateIssued) = :year AND a.visitor = :visitor")
    Page<Appearance> findByPurposeAndYearContainingIgnoreCase(@Param("purpose")String purpose, @Param("year")Integer year, @Param("visitor")Visitor visitor, Pageable pageable);

    @Query("SELECT a FROM Appearance a WHERE a.purpose.purpose = :purpose AND MONTH(a.dateIssued) = :month AND YEAR(a.dateIssued) = :year AND a.visitor = :visitor")
    Page<Appearance> findByPurposeAndMonthAndYearContainingIgnoreCase(@Param("purpose")String purpose,@Param("month") Integer  month, @Param("year")Integer year, @Param("visitor")Visitor visitor, Pageable pageable);

    @Query("SELECT a FROM Appearance a WHERE a.dateIssued = :dateIssued AND a.visitor = :visitor")
    Page<Appearance> findByDateIssued(@Param("dateIssued")LocalDate dateIssued, @Param("visitor")Visitor visitor, Pageable pageable);

    @Query("SELECT a FROM Appearance a WHERE a.visitor = :visitor")
    Page<Appearance> findAppearanceByVisitor(@Param("visitor") Visitor visitor,  Pageable pageable);

    @Query("SELECT a FROM Appearance a  WHERE MONTH(a.dateIssued) = :month AND a.visitor = :visitor")
    Page<Appearance> findAppearanceByMonthDateIssued(@Param("month") Integer  month, Visitor visitor, Pageable pageable);

    @Query("SELECT a FROM Appearance a  WHERE MONTH(a.dateIssued) = :month AND YEAR(a.dateIssued) = :year AND a.visitor = :visitor")
    Page<Appearance> findAppearanceByMonthAndYearDateIssued(@Param("month") Integer  month, @Param("year")Integer year, @Param("visitor")Visitor visitor, Pageable pageable);

    @Query("SELECT a FROM Appearance a WHERE YEAR(a.dateIssued) = :year AND a.visitor = :visitor")
    Page<Appearance> findAppearanceByYearDateIssued(@Param("year") Integer year, @Param("visitor")Visitor visitor, Pageable pageable);

    @Query("SELECT DISTINCT YEAR(a.dateIssued) FROM Appearance a ORDER BY YEAR(a.dateIssued) ASC")
    List<Integer> findAllDistinctYear();


    @Query("SELECT a FROM Appearance a ORDER BY YEAR(a.dateIssued) ASC")
    Page<Appearance> findAppearanceOrderByDateIssuedASC(Pageable pageable);


    @Query("SELECT a FROM Appearance a WHERE a.visitor.name= :name")
    Optional<Appearance> findAppearanceByVisitorName(@Param("name") String name);


}
