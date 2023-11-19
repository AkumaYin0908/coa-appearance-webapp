package com.coa.repository;

import com.coa.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PositionRepository extends JpaRepository<Position,Long> {


    @Query("SELECT p FROM Position p where p.name= :name")
    Optional<Position> findPositionByName(@Param("name")String name);
}
