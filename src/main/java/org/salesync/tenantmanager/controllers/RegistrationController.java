package org.salesync.tenantmanager.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.salesync.tenantmanager.dtos.RegistrationRequestDto;
import org.salesync.tenantmanager.dtos.RegistrationResponseDto;
import org.salesync.tenantmanager.services.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/registrations")
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    /**
     * A customization microservice calls this on its own startup/deploy to register the
     * customization point it implements for a tenant.
     */
    @PostMapping
    public ResponseEntity<RegistrationResponseDto> register(@Valid @RequestBody RegistrationRequestDto request) {
        return ResponseEntity.ok(registrationService.register(request));
    }

    /**
     * Queried by the Customization API Gateway to resolve the target for a
     * tenant + customization point pair.
     */
    @GetMapping("/{realm}/{customizationPoint}")
    public ResponseEntity<RegistrationResponseDto> getActiveRegistration(@PathVariable String realm, @PathVariable String customizationPoint) {
        return ResponseEntity.ok(registrationService.getActiveRegistration(realm, customizationPoint));
    }

    /**
     * Queried by the frontend's customization registry hook to know which points a
     * tenant currently has overridden.
     */
    @GetMapping("/{realm}")
    public ResponseEntity<List<RegistrationResponseDto>> getActiveRegistrationsForTenant(@PathVariable String realm) {
        return ResponseEntity.ok(registrationService.getActiveRegistrationsForTenant(realm));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deregister(@PathVariable UUID id) {
        registrationService.deregister(id);
        return ResponseEntity.noContent().build();
    }
}
