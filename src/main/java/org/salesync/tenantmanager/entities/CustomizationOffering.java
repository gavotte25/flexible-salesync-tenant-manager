package org.salesync.tenantmanager.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "customization_offerings", uniqueConstraints = @UniqueConstraint(columnNames = {"offering_key", "customization_point"}))
@Getter
@Setter
@NoArgsConstructor
public class CustomizationOffering {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "offering_key", nullable = false)
    private String offeringKey;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "customization_point", nullable = false)
    private String customizationPoint;

    @Column(name = "target_service_dns", nullable = false)
    private String targetServiceDns;

    @Column(name = "target_port", nullable = false)
    private int targetPort;

    @Column(name = "version", nullable = false)
    private String version;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();
}
