package org.salesync.tenantmanager.repositories;

import org.salesync.tenantmanager.entities.CustomizationRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomizationRegistrationRepository extends JpaRepository<CustomizationRegistration, UUID> {
    // Unique on (realmName, customizationPoint) regardless of active status - used to
    // upsert on re-registration so a previously-deregistered row is reactivated in place
    // instead of hitting the unique-constraint violation a fresh insert would cause.
    Optional<CustomizationRegistration> findByRealmNameAndCustomizationPoint(String realmName, String customizationPoint);

    Optional<CustomizationRegistration> findByRealmNameAndCustomizationPointAndActiveTrue(String realmName, String customizationPoint);

    List<CustomizationRegistration> findAllByRealmNameAndActiveTrue(String realmName);
}
