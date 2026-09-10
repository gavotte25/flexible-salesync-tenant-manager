package org.salesync.tenantmanager.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class RegistrationResponseDto {
    private UUID id;
    private String realmName;
    private String customizationPoint;
    private String targetServiceDns;
    private int targetPort;
    private String version;
    private boolean active;
    private Instant updatedAt;
}
