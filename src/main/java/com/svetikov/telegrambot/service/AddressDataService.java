package com.svetikov.telegrambot.service;

import com.svetikov.telegrambot.model.AddressData;
import com.svetikov.telegrambot.repository.RepositoryAddressData;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressDataService implements ServiceAddressData {
    private final RepositoryAddressData repositoryAddressData;

    public AddressDataService(RepositoryAddressData repositoryAddressData) {
        this.repositoryAddressData = repositoryAddressData;
    }

    @Override
    public void addAddress(AddressData addressData) {
        repositoryAddressData.save(addressData);
    }

    @Override
    public Optional<AddressData> getByIdAddressData(long id) {
        return repositoryAddressData.findById(id);
    }

    @Override
    public List<AddressData> allAddressData() {
        return repositoryAddressData.findAll();
    }

    @Override
    public boolean deleteAddressData(long id) {
        repositoryAddressData.deleteById(id);
        return true;
    }

    @Override
    public void addListAddress(List<AddressData> list) {
        repositoryAddressData.saveAll(list);
    }
}
