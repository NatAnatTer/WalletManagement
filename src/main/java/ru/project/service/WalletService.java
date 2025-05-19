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
    public void changeBalance(WalletOperationDto walletOperationDto) {
        try {
            synchronized (walletOperationDto.walletId()) {
                UUID id = UUID.fromString(walletOperationDto.walletId());
                switch (walletOperationDto.operationType()) {
                    case DEPOSIT -> {
                        Wallet wallet = findById(id);
                        changeAmountOfWallet(wallet, walletOperationDto.amount());
                    }
                    case WITHDRAW -> {
                        Wallet wallet = findById(id);
                        checkIfAvailableAmount(wallet.getAmount(), walletOperationDto.amount());
                        changeAmountOfWallet(wallet, -walletOperationDto.amount());
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            throw new UUIDFormatException(e.getMessage());
        } catch (NullPointerException e) {
            throw new IncorrectFormatDataException(e.getMessage());
        }

    }

    public WalletDto getWallet(String id) {
        try {
            Wallet wallet = repository.findById(UUID.fromString(id)).orElseThrow(() -> new ResourceNotFoundException(id));
            return mapToDto(wallet);
        } catch (IllegalArgumentException e) {
            throw new UUIDFormatException(e.getMessage());
        }

    }

    private Wallet findById(UUID id) {
        try {
            return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id.toString()));
        } catch (IllegalArgumentException e) {
            throw new UUIDFormatException(e.getMessage());
        }

    }

    private void checkIfAvailableAmount(long amount, long withdraw) {
        if ((amount - withdraw) < 0) {
            throw new NotEnoughAmountException(String.valueOf(amount));
        }
    }


    private void changeAmountOfWallet(Wallet wallet, long amount) {
        wallet.setAmount(wallet.getAmount() + amount);
        if (wallet.getAmount() < 0) {
            throw new NotEnoughAmountException(String.valueOf(amount));
        } else {
            repository.save(wallet);
        }
    }

    private WalletDto mapToDto(Wallet wallet) {
        return new WalletDto(wallet.getWalletId().toString(), wallet.getAmount());
    }

}
