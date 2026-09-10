package org.salesync.tenantmanager.services;

import org.salesync.tenantmanager.dtos.RabbitMQMessageDto;

public interface RabbitMQService {
    void dispatchMessage(RabbitMQMessageDto message);
}
