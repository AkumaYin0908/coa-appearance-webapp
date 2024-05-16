package com.coa.repository.address;

import com.coa.model.address.Municipality;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MunicipalityRepository  extends JpaRepository<Municipality,Long> {
}
