package ru.project.dto;

import ru.project.entity.OperationType;

public record WalletOperationDto(String walledId, OperationType operationType, long amount) {
}
