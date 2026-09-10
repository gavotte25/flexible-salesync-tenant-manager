package org.salesync.tenantmanager.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "tenants", uniqueConstraints = @UniqueConstraint(columnNames = "realm_name"))
@Getter
@Setter
@NoArgsConstructor
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "realm_name", nullable = false)
    private String realmName;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    public Tenant(String realmName) {
        this.realmName = realmName;
    }
}
