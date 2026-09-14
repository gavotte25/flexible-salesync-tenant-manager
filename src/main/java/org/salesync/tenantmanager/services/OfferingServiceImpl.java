package org.salesync.tenantmanager.services;

import lombok.RequiredArgsConstructor;
import org.salesync.tenantmanager.dtos.ActivationRequestDto;
import org.salesync.tenantmanager.dtos.OfferingDto;
import org.salesync.tenantmanager.dtos.OfferingRegistrationDto;
import org.salesync.tenantmanager.dtos.RegistrationRequestDto;
import org.salesync.tenantmanager.entities.CustomizationOffering;
import org.salesync.tenantmanager.repositories.CustomizationOfferingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfferingServiceImpl implements OfferingService {
    private final CustomizationOfferingRepository offeringRepository;
    private final RegistrationService registrationService;

    @Override
    public OfferingDto registerOffering(OfferingRegistrationDto request) {
        CustomizationOffering offering = offeringRepository
                .findByOfferingKeyAndCustomizationPoint(request.getOfferingKey(), request.getCustomizationPoint())
                .orElseGet(CustomizationOffering::new);
        offering.setOfferingKey(request.getOfferingKey());
        offering.setName(request.getName());
        offering.setDescription(request.getDescription());
        offering.setCustomizationPoint(request.getCustomizationPoint());
        offering.setTargetServiceDns(request.getTargetServiceDns());
        offering.setTargetPort(request.getTargetPort());
        offering.setVersion(request.getVersion());
        offering.setUpdatedAt(Instant.now());
        offeringRepository.save(offering);
        return toDto(request.getOfferingKey(), offeringRepository.findAllByOfferingKey(request.getOfferingKey()));
    }

    @Override
    public List<OfferingDto> listOfferings() {
        Map<String, List<CustomizationOffering>> byKey = offeringRepository.findAllByOrderByOfferingKey().stream()
                .collect(Collectors.groupingBy(CustomizationOffering::getOfferingKey, LinkedHashMap::new, Collectors.toList()));
        return byKey.entrySet().stream().map(entry -> toDto(entry.getKey(), entry.getValue())).toList();
    }

    @Override
    public OfferingDto getOffering(String offeringKey) {
        List<CustomizationOffering> offerings = offeringRepository.findAllByOfferingKey(offeringKey);
        if (offerings.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unknown offering: " + offeringKey);
        }
        return toDto(offeringKey, offerings);
    }

    @Override
    public void activate(ActivationRequestDto request) {
        List<CustomizationOffering> offerings = offeringRepository.findAllByOfferingKey(request.getOfferingKey());
        if (offerings.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unknown offering: " + request.getOfferingKey());
        }
        for (CustomizationOffering offering : offerings) {
            RegistrationRequestDto registrationRequest = new RegistrationRequestDto();
            registrationRequest.setRealmName(request.getRealmName());
            registrationRequest.setCustomizationPoint(offering.getCustomizationPoint());
            registrationRequest.setTargetServiceDns(offering.getTargetServiceDns());
            registrationRequest.setTargetPort(offering.getTargetPort());
            registrationRequest.setVersion(offering.getVersion());
            registrationRequest.setConfig(request.getConfig());
            registrationService.register(registrationRequest);
        }
    }

    @Override
    public void deactivate(String realmName, String offeringKey) {
        List<CustomizationOffering> offerings = offeringRepository.findAllByOfferingKey(offeringKey);
        for (CustomizationOffering offering : offerings) {
            registrationService.deregisterIfExists(realmName, offering.getCustomizationPoint());
        }
    }

    private OfferingDto toDto(String offeringKey, List<CustomizationOffering> offerings) {
        CustomizationOffering first = offerings.get(0);
        List<String> points = offerings.stream().map(CustomizationOffering::getCustomizationPoint).toList();
        return new OfferingDto(offeringKey, first.getName(), first.getDescription(), points);
    }
}
