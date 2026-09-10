package org.salesync.tenantmanager.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.salesync.tenantmanager.dtos.ActivationRequestDto;
import org.salesync.tenantmanager.dtos.OfferingDto;
import org.salesync.tenantmanager.dtos.OfferingRegistrationDto;
import org.salesync.tenantmanager.services.OfferingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OfferingController {
    private final OfferingService offeringService;

    @PostMapping("/offerings")
    public ResponseEntity<OfferingDto> registerOffering(@Valid @RequestBody OfferingRegistrationDto request) {
        return ResponseEntity.ok(offeringService.registerOffering(request));
    }

    @GetMapping("/offerings")
    public ResponseEntity<List<OfferingDto>> listOfferings() {
        return ResponseEntity.ok(offeringService.listOfferings());
    }

    @GetMapping("/offerings/{offeringKey}")
    public ResponseEntity<OfferingDto> getOffering(@PathVariable String offeringKey) {
        return ResponseEntity.ok(offeringService.getOffering(offeringKey));
    }

    @PostMapping("/activations")
    public ResponseEntity<Void> activate(@Valid @RequestBody ActivationRequestDto request) {
        offeringService.activate(request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/activations/{realm}/{offeringKey}")
    public ResponseEntity<Void> deactivate(@PathVariable String realm, @PathVariable String offeringKey) {
        offeringService.deactivate(realm, offeringKey);
        return ResponseEntity.noContent().build();
    }
}
