package org.salesync.tenantmanager.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OfferingDto {
    private String offeringKey;
    private String name;
    private String description;
    private List<String> customizationPoints;
}
