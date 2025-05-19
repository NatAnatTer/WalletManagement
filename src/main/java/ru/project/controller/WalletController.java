package ru.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.project.dto.WalletDto;
import ru.project.dto.WalletOperationDto;
import ru.project.service.WalletService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class WalletController {

    private final WalletService service;
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/v1/wallets/{walletUuid}")
    public WalletDto getBalance(@PathVariable String walletUuid) {
       return service.getWallet(walletUuid);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/v1/wallet")
    public void createTransaction(@RequestBody WalletOperationDto walletOperationDto) {
        service.changeBalance(walletOperationDto);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public void createWallet() {
        service.createWallet();
    }
}
