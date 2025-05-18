package ru.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.project.dto.WalletOperationDto;
import ru.project.entity.Wallet;
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

    public void changeBalance(WalletOperationDto walletOperationDto) {
      switch (walletOperationDto.operationType()){
        case DEPOSIT -> {
            findById(UUID.fromString(walletOperationDto.walledId()));


        }
        case WITHDRAW -> {

        }
      }

    }

    private Wallet findById(UUID id){
      return repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Кошелек с UID: " + id + " не найден"));
    }

    private void checkIfAvailableAmount(Long amount, long withdraw){
        if((amount - withdraw) <0){

        }
    }
}
