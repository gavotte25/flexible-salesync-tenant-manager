package org.salesync.tenantmanager.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.salesync.tenantmanager.enums.ActionType;

@Getter
@Setter
@NoArgsConstructor
public class RabbitMQMessageDto {
    private ActionType actionType;
    private Object payload;
}
