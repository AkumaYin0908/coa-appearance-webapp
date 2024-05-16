package com.coa.repository.address;

import com.coa.model.address.Barangay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarangayRepository extends JpaRepository<Barangay,Long> {
}
