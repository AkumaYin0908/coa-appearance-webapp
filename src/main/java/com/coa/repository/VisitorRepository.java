package com.coa.repository;

import com.coa.constants.query_contants.VisitorConstant;
import com.coa.model.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor,Long> {

    @Query(value = VisitorConstant.FIND_VISITOR_BY_NAME,nativeQuery = true)
    Optional<Visitor> findByName(String name);



    //wrapping expected object(which is the Map object) with List<> to avoid NonUniqueResultException
    @Query(value = VisitorConstant.FIND_NAMES,nativeQuery = true)
    List<Map<Long, String>> findNames();

}
