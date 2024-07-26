package com.coa.repository;

import com.coa.constants.query_contants.CourtesyTitleConstant;
import com.coa.model.CourtesyTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourtesyTitleRepository extends JpaRepository<CourtesyTitle,Long> {

    @Query(value = CourtesyTitleConstant.FIND_BY_TITLE,nativeQuery = true)
    Optional<CourtesyTitle> findByTitle(String title);
}
