package ru.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.project.dto.WalletDto;
import ru.project.dto.WalletOperationDto;
import ru.project.entity.Wallet;
import ru.project.exception.NotEnoughAmountException;
import ru.project.exception.ResourceNotFoundException;
import ru.project.repository.WalletRepository;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class WalletService {
    private final WalletRepository repository;

    public void createWallet() {
        Wallet wallet = new Wallet();
        wallet.setAmount(0L);
        repository.save(wallet);
    }
@Transactional
    public void changeBalance(WalletOperationDto walletOperationDto) {
        switch (walletOperationDto.operationType()) {
            case DEPOSIT -> {
                Wallet wallet = findById(UUID.fromString(walletOperationDto.walledId()));
                changeAmountOfWallet(wallet, walletOperationDto.amount());

            }
            case WITHDRAW -> {
                Wallet wallet = findById(UUID.fromString(walletOperationDto.walledId()));
                checkIfAvailableAmount(wallet.getAmount(), walletOperationDto.amount());
                changeAmountOfWallet(wallet, walletOperationDto.amount());
            }
        }

    }

    public WalletDto getWallet(String id){
      Wallet wallet =  repository.findById(UUID.fromString(id)).orElseThrow(() ->new ResourceNotFoundException("Кошелек не найден"));
     return mapToDto(wallet);
    }

    private Wallet findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Кошелек не найден"));
    }

    private void checkIfAvailableAmount(long amount, long withdraw) {
        if ((amount - withdraw) < 0) {
            throw new NotEnoughAmountException("Недостаточно средств для выполнения операции");
        }
    }


    private void changeAmountOfWallet(Wallet wallet, long amount) {
        wallet.setAmount(wallet.getAmount() + amount);
        if (wallet.getAmount() < 0) {
            throw new NotEnoughAmountException("Недостаточно средств для выполнения операции");
        } else {
            repository.save(wallet);
        }
    }

    private WalletDto mapToDto(Wallet wallet){
       return new WalletDto(wallet.getWalledId().toString(), wallet.getAmount());
    }

}
