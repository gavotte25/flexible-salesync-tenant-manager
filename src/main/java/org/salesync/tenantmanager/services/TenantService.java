package org.salesync.tenantmanager.services;

public interface TenantService {
    /**
     * Idempotently records a tenant. Called when the "auth" tenant-onboarding event is
     * received, and safe to call again if the same realm is seen twice.
     */
    void ensureTenantExists(String realmName);
}
