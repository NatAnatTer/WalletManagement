package ru.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.project.entity.Wallet;

import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {

}
