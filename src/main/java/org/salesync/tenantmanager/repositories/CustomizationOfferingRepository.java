package org.salesync.tenantmanager.repositories;

import org.salesync.tenantmanager.entities.CustomizationOffering;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomizationOfferingRepository extends JpaRepository<CustomizationOffering, UUID> {
    Optional<CustomizationOffering> findByOfferingKeyAndCustomizationPoint(String offeringKey, String customizationPoint);

    List<CustomizationOffering> findAllByOfferingKey(String offeringKey);

    List<CustomizationOffering> findAllByOrderByOfferingKey();
}
