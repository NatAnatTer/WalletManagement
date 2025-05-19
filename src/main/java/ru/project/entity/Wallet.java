package ru.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldNameConstants
public class Wallet implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "wallet_id")
    private UUID walletId;

    private long amount;

}
