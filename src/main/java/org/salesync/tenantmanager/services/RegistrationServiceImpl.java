package org.salesync.tenantmanager.services;

import lombok.RequiredArgsConstructor;
import org.salesync.tenantmanager.dtos.RegistrationRequestDto;
import org.salesync.tenantmanager.dtos.RegistrationResponseDto;
import org.salesync.tenantmanager.entities.CustomizationRegistration;
import org.salesync.tenantmanager.repositories.CustomizationRegistrationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final CustomizationRegistrationRepository registrationRepository;
    private final TenantService tenantService;

    @Override
    public RegistrationResponseDto register(RegistrationRequestDto request) {
        tenantService.ensureTenantExists(request.getRealmName());

        CustomizationRegistration registration = registrationRepository
                .findByRealmNameAndCustomizationPoint(request.getRealmName(), request.getCustomizationPoint())
                .orElseGet(CustomizationRegistration::new);
        registration.setRealmName(request.getRealmName());
        registration.setCustomizationPoint(request.getCustomizationPoint());
        registration.setTargetServiceDns(request.getTargetServiceDns());
        registration.setTargetPort(request.getTargetPort());
        registration.setVersion(request.getVersion());
        registration.setActive(true);
        registration.setUpdatedAt(Instant.now());
        return toDto(registrationRepository.save(registration));
    }

    @Override
    public RegistrationResponseDto getActiveRegistration(String realmName, String customizationPoint) {
        return registrationRepository.findByRealmNameAndCustomizationPointAndActiveTrue(realmName, customizationPoint)
                .map(this::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No active customization registered for tenant '%s' at point '%s'".formatted(realmName, customizationPoint)));
    }

    @Override
    public List<RegistrationResponseDto> getActiveRegistrationsForTenant(String realmName) {
        return registrationRepository.findAllByRealmNameAndActiveTrue(realmName).stream().map(this::toDto).toList();
    }

    @Override
    public void deregister(UUID id) {
        CustomizationRegistration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registration not found: " + id));
        registration.setActive(false);
        registration.setUpdatedAt(Instant.now());
        registrationRepository.save(registration);
    }

    @Override
    public void deregisterIfExists(String realmName, String customizationPoint) {
        registrationRepository.findByRealmNameAndCustomizationPointAndActiveTrue(realmName, customizationPoint)
                .ifPresent(registration -> {
                    registration.setActive(false);
                    registration.setUpdatedAt(Instant.now());
                    registrationRepository.save(registration);
                });
    }

    private RegistrationResponseDto toDto(CustomizationRegistration registration) {
        return new RegistrationResponseDto(
                registration.getId(),
                registration.getRealmName(),
                registration.getCustomizationPoint(),
                registration.getTargetServiceDns(),
                registration.getTargetPort(),
                registration.getVersion(),
                registration.isActive(),
                registration.getUpdatedAt());
    }
}
