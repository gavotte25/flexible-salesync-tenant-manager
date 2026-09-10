package org.salesync.tenantmanager.repositories;

import org.salesync.tenantmanager.entities.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TenantRepository extends JpaRepository<Tenant, UUID> {
    Optional<Tenant> findByRealmName(String realmName);

    boolean existsByRealmName(String realmName);
}
