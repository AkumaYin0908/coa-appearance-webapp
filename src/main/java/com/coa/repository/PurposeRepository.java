package com.coa.repository;

import com.coa.model.Purpose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurposeRepository extends JpaRepository<Purpose,Long> {

}
