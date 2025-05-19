package ru.project.entity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.project.dto.WalletDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WalletMapper {
    @Mapping(target = "walletId", source = "walletDto.walletId")
    Wallet toWallet(WalletDto walletDto, Long amount);

    WalletDto toWalletDto(Wallet wallet);
}
