package com.coa.repository;

import com.coa.constants.query_contants.PositionConstant;
import com.coa.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    @Query(value = PositionConstant.FIND_BY_TITLE,nativeQuery = true)
    Optional<Position> findByTitle(String title);

    @Query(value = PositionConstant.FIND_TITLES,nativeQuery = true)
    List<Map<Long, String>> findTitles();

}
