package org.salesync.tenantmanager.services;

import lombok.RequiredArgsConstructor;
import org.salesync.tenantmanager.entities.Tenant;
import org.salesync.tenantmanager.repositories.TenantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TenantServiceImpl implements TenantService {
    private final TenantRepository tenantRepository;
    private static final Logger logger = LoggerFactory.getLogger(TenantServiceImpl.class);

    @Override
    public void ensureTenantExists(String realmName) {
        if (tenantRepository.existsByRealmName(realmName)) {
            return;
        }
        tenantRepository.save(new Tenant(realmName));
        logger.info("Registered new tenant in Tenant Manager: {}", realmName);
    }
}
