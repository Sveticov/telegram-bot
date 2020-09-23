package com.svetikov.telegrambot.repository;

import com.svetikov.telegrambot.model.AddressData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryAddressData extends JpaRepository<AddressData,Long> {
}
