package org.salesync.tenantmanager.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequestDto {
    @NotBlank
    private String realmName;

    @NotBlank
    private String customizationPoint;

    @NotBlank
    private String targetServiceDns;

    @Min(1)
    @Max(65535)
    private int targetPort;

    @NotBlank
    private String version;

    /** Opaque per-tenant config (JSON string), forwarded as-is to the target customization. */
    private String config;
}
