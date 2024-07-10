package com.ecommerce.backend.repository;

import com.ecommerce.backend.entity.PlatFormCharges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatFormChargesRepository extends JpaRepository<PlatFormCharges, Long> {
}
