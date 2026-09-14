package org.salesync.tenantmanager.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivationRequestDto {
    @NotBlank
    private String realmName;

    @NotBlank
    private String offeringKey;

    /** Opaque per-tenant config (JSON string) for offerings that need one, e.g. a Slack webhook URL. */
    private String config;
}
