package org.salesync.tenantmanager.services;

import lombok.RequiredArgsConstructor;
import org.salesync.tenantmanager.dtos.RabbitMQMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQServiceImpl implements RabbitMQService {
    private final TenantService tenantService;
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQServiceImpl.class);

    @Override
    public void dispatchMessage(RabbitMQMessageDto message) {
        logger.info("Received message action: {}", message.getActionType());
        switch (message.getActionType()) {
            case INIT_TYPES -> tenantService.ensureTenantExists((String) message.getPayload());
        }
    }
}
