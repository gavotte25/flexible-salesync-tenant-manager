package org.salesync.tenantmanager.controllers;

import lombok.RequiredArgsConstructor;
import org.salesync.tenantmanager.dtos.RabbitMQMessageDto;
import org.salesync.tenantmanager.services.RabbitMQService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class RabbitMQController {
    private final RabbitMQService rabbitMQService;

    @RabbitListener(queues = "tenant-manager-queue", ackMode = "AUTO")
    public void receiveMessage(RabbitMQMessageDto message) {
        rabbitMQService.dispatchMessage(message);
    }
}
