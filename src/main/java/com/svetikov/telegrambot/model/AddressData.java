package com.svetikov.telegrambot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "address_data")
public class AddressData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idAddress;
    private String messageAddress;
    private boolean statusAddress;
    private TypeAddress typeAddress;
    private int byteAddress;
    private int biteAddress;

}
