package org.salesync.tenantmanager.services;

import org.salesync.tenantmanager.dtos.RegistrationRequestDto;
import org.salesync.tenantmanager.dtos.RegistrationResponseDto;

import java.util.List;
import java.util.UUID;

public interface RegistrationService {
    RegistrationResponseDto register(RegistrationRequestDto request);

    RegistrationResponseDto getActiveRegistration(String realmName, String customizationPoint);

    List<RegistrationResponseDto> getActiveRegistrationsForTenant(String realmName);

    void deregister(UUID id);

    void deregisterIfExists(String realmName, String customizationPoint);
}
