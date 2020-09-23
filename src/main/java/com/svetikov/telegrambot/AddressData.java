package com.svetikov.telegrambot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressData {
    private String messageAddress;
    private boolean statusAddress;
    private TypeAddress typeAddress;
    private int byteAddress;
    private int biteAddress;

}
