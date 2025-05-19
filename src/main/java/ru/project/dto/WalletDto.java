package ru.project.dto;

public record WalletDto(String walletId, Long amount) {
}

/*
public class WalletDto {
    private final String walletId;
    private final Long amount;

    public WalletDto(String walletId, Long amount) {
        this.walletId = walletId;
        this.amount = amount;
    }

    public String getWalletId() {
        return walletId;
    }

    public Long getAmount() {
        return amount;
    }
}
 */
