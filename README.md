# flexible-salesync-tenant-manager

MiSC-Cloud Tenant Manager. Tracks tenants and the customization microservices they've registered per customization point, so the Customization API Gateway can resolve which tenant-specific service to call. See `doc/misc-cloud-implementation-plan.md` in the top-level repo for the full architecture.

## API

- `POST /registrations` — a customization microservice self-registers `{realmName, customizationPoint, targetServiceDns, targetPort, version}`.
- `GET /registrations/{realm}/{customizationPoint}` — resolve the active registration (used by the Customization API Gateway).
- `GET /registrations/{realm}` — list a tenant's active registrations (used by the frontend's customization registry hook).
- `DELETE /registrations/{id}` — deregister.

## Events

Consumes the existing `topic-exchange` / `auth` routing key (the same tenant-onboarding event `authentication` already publishes and `type` already consumes) via its own `tenant-manager-queue`, to automatically create a tenant record when a company registers.
