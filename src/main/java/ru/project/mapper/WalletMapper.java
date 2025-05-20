package ru.project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.project.dto.WalletDto;
import ru.project.entity.Wallet;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WalletMapper {
    @Mapping(target = "walletId", source = "walletDto.walletId")
    Wallet toWallet(WalletDto walletDto);

    @Mapping(target = "walletId", source = "wallet.walletId")
    WalletDto toWalletDto(Wallet wallet);

}
