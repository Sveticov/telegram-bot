package com.svetikov.telegrambot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
//@NoArgsConstructor
@Entity
@Table(name = "address_data")
public class AddressData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idAddress;
    private String plcNameAddress;
    private String messageAddress;
    private boolean statusAddress;
    private TypeAddress typeAddress;
    private int byteAddress;
    private int biteAddress;


    public AddressData(String plcNameAddress,String messageAddress, boolean statusAddress, TypeAddress typeAddress, int byteAddress, int biteAddress) {
        this.plcNameAddress=plcNameAddress;
        this.messageAddress = messageAddress;
        this.statusAddress = statusAddress;
        this.typeAddress = typeAddress;
        this.byteAddress = byteAddress;
        this.biteAddress = biteAddress;
    }

    public AddressData() {
    }
}
