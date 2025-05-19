package ru.project.dto;

import ru.project.entity.OperationType;

public record WalletOperationDto(String walletId, OperationType operationType, long amount) {
}
