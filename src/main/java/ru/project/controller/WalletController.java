package ru.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.project.dto.WalletDto;
import ru.project.dto.WalletOperationDto;
import ru.project.service.WalletService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class WalletController {

    private final WalletService service;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/wallets/{walletUuid}")
    public ResponseEntity<WalletDto> getBalance(@PathVariable String walletUuid) {
        return ResponseEntity.ok(service.getWallet(walletUuid));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/wallet")
    public void createTransaction(@RequestBody WalletOperationDto walletOperationDto) {
        service.changeBalance(walletOperationDto);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public ResponseEntity<WalletDto> createWallet(@RequestBody WalletDto walletDto) {
        WalletDto wallet = service.createWallet(walletDto);
        return ResponseEntity.ok(wallet);
    }
}
