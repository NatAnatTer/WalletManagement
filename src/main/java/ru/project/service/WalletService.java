package ru.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.project.dto.WalletDto;
import ru.project.dto.WalletOperationDto;
import ru.project.entity.Wallet;
import ru.project.mapper.WalletMapper;
import ru.project.exception.*;
import ru.project.repository.WalletRepository;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class WalletService {
    private final WalletRepository repository;
    private final WalletMapper mapper;

    public WalletDto createWallet(WalletDto walletDto) {
        Wallet wallet = new Wallet();
        wallet.setAmount(walletDto.amount());
        return mapToDto(repository.save(wallet));
    }

    @Transactional
    public WalletDto changeBalance(WalletOperationDto walletOperationDto) {
        Wallet withNewBalance = new Wallet();
        try {
            synchronized (walletOperationDto.walletId()) {
                switch (walletOperationDto.operationType()) {
                    case DEPOSIT -> {
                        Wallet wallet = findById(walletOperationDto.walletId());
                        withNewBalance = changeAmountOfWallet(wallet, walletOperationDto.amount());
                    }
                    case WITHDRAW -> {
                        Wallet wallet = findById(walletOperationDto.walletId());
                        checkIfAvailableAmount(wallet.getAmount(), walletOperationDto.amount());
                        withNewBalance = changeAmountOfWallet(wallet, -walletOperationDto.amount());
                    }
                }
            }
        } catch (NullPointerException e) {
            throw new IncorrectFormatDataException(e.getMessage());
        }
        return mapper.toWalletDto(withNewBalance);
    }

    public WalletDto getWallet(String id) {
        Wallet wallet = findById(id);
        return mapToDto(wallet);
    }

    private Wallet findById(String id) {
        try {
            return repository.findById(UUID.fromString(id)).orElseThrow(() -> new ResourceNotFoundException(id));
        } catch (IllegalArgumentException e) {
            throw new UUIDFormatException(e.getMessage());
        }

    }

    private void checkIfAvailableAmount(long amount, long withdraw) {
        if ((amount - withdraw) < 0) {
            throw new NotEnoughAmountException(String.valueOf(amount));
        }
    }


    private Wallet changeAmountOfWallet(Wallet wallet, long amount) {
        wallet.setAmount(wallet.getAmount() + amount);
        if (wallet.getAmount() < 0) {
            throw new NotEnoughAmountException(String.valueOf(amount));
        } else {
            return repository.save(wallet);
        }
    }

    private WalletDto mapToDto(Wallet wallet) {
        return mapper.toWalletDto(wallet);
    }

}
