package com.svetikov.telegrambot.service;

import com.svetikov.telegrambot.model.AddressData;

import java.util.List;
import java.util.Optional;

public interface ServiceAddressData {
    void addAddress(AddressData addressData);
    Optional<AddressData> getByIdAddressData(long id);
    List<AddressData> allAddressData();
    boolean deleteAddressData(long id);
    void addListAddress(List<AddressData> list);
}
