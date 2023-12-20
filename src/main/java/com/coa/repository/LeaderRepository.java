package com.coa.repository;

import com.coa.model.Leader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface LeaderRepository extends JpaRepository<Leader,Long> {

    Page<Leader> findByNameContainingIgnoreCase(String name, Pageable pageable);


    @Query("SELECT l FROM Leader l WHERE l.name = :name ")
    Optional<Leader> findLeaderByName(@Param("name")String name);


    @Query("SELECT l FROM Leader l WHERE l.id <> :id AND l.name = :name")
    Optional<Leader> findLeaderByName(@Param("id") Long id, @Param("name")String name);

    @Query("UPDATE Leader l SET  l.inCharge = :inCharge WHERE l.id = :id")
    @Modifying
    void updateInChargeStatus(Long id, boolean inCharge);


    @Query("UPDATE Leader l SET l.inCharge = CASE WHEN l.id = :currentInCharge THEN false WHEN l.id = :newlyInCharge THEN true END WHERE l.id IN (:currentInCharge, :newlyInCharge)")
    @Modifying
    void updateInChargeStatus(@Param("currentInCharge") Long  currentInCharge, @Param("newlyInCharge") Long newlyInCharge);


    @Query("SELECT l FROM Leader l WHERE l.inCharge = :inCharge")
    Optional<Leader> findLeaderByInChargeStatus(@Param("inCharge")boolean inCharge);
    @Query("SELECT COUNT(l.inCharge) FROM Leader l where l.inCharge = true")
    Long countByInCharge();
}
