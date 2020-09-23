package com.svetikov.telegrambot.repository;

import com.svetikov.telegrambot.model.PlcConnection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryPLC extends JpaRepository<PlcConnection,Long> {

}
