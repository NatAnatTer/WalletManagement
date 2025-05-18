package ru.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.project.dto.WalletDto;
import ru.project.dto.WalletOperationDto;
import ru.project.service.WalletService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class WalletController {

  private final WalletService service;

    @GetMapping("/v1/wallets/{wallet_uuid}")
    public WalletDto getBalance(@RequestParam String wallet_uuid) {
        return null;
    }

    @PostMapping("/v1/wallet")
    public void createTransaction(@RequestBody WalletOperationDto walletOperationDto) {
service.changeBalance(walletOperationDto);
    }

    @PostMapping("/create")
    public void createWallet() {
        service.createWallet();
    }
}
