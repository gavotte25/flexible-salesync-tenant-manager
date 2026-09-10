package org.salesync.tenantmanager.services;

import org.salesync.tenantmanager.dtos.ActivationRequestDto;
import org.salesync.tenantmanager.dtos.OfferingDto;
import org.salesync.tenantmanager.dtos.OfferingRegistrationDto;

import java.util.List;

public interface OfferingService {
    OfferingDto registerOffering(OfferingRegistrationDto request);

    List<OfferingDto> listOfferings();

    OfferingDto getOffering(String offeringKey);

    void activate(ActivationRequestDto request);

    void deactivate(String realmName, String offeringKey);
}
