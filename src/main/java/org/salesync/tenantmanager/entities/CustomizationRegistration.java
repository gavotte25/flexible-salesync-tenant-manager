package org.salesync.tenantmanager.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

/**
 * One tenant's registered override for a customization point (a stable key such as
 * "record.list.view" that the main product exposes as a MiSC-Cloud pointcut). Discovery
 * of the customization microservice itself is done via Kubernetes in-cluster DNS, so
 * targetServiceDns/targetPort are plain Service coordinates, not a service-registry entry.
 */
@Entity
@Table(name = "customization_registrations", uniqueConstraints = @UniqueConstraint(columnNames = {"realm_name", "customization_point"}))
@Getter
@Setter
@NoArgsConstructor
public class CustomizationRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "realm_name", nullable = false)
    private String realmName;

    @Column(name = "customization_point", nullable = false)
    private String customizationPoint;

    @Column(name = "target_service_dns", nullable = false)
    private String targetServiceDns;

    @Column(name = "target_port", nullable = false)
    private int targetPort;

    @Column(name = "version", nullable = false)
    private String version;

    /**
     * Opaque, per-tenant JSON blob (webhook URLs, template maps, etc.) that rides along
     * with the activation instead of needing a bespoke config subsystem per customization.
     * Tenant Manager never parses it - only the target customization microservice does.
     */
    @Column(name = "config", columnDefinition = "TEXT")
    private String config;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();
}
