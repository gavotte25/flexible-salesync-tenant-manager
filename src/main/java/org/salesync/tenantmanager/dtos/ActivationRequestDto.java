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
}
