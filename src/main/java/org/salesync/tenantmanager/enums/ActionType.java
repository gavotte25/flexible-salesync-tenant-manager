package org.salesync.tenantmanager.enums;

/**
 * Mirrors org.salesync.authentication.enums.ActionType / com.salesync.typeservice.enums.ActionType.
 * Kept as a separate copy (no shared library submodule exists yet in this repo) so this
 * service can deserialize the same "auth" routing-key messages authentication publishes.
 */
public enum ActionType {
    INIT_TYPES
}
