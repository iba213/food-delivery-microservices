package com.champsoft.fooddelivery.drivers.infrastructure.persistence;

import com.champsoft.fooddelivery.drivers.domain.model.DriverStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataDriverRepository extends JpaRepository<DriverJpaEntity, UUID> {

    Optional<DriverJpaEntity> findFirstByStatus(DriverStatus status);
}
