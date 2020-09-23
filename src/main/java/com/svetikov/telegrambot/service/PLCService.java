package com.svetikov.telegrambot.service;

import com.svetikov.telegrambot.model.PlcConnection;
import com.svetikov.telegrambot.repository.RepositoryPLC;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PLCService implements ServicePLC {
    private final RepositoryPLC repositoryPLC;

    public PLCService(RepositoryPLC repositoryPLC) {
        this.repositoryPLC = repositoryPLC;
    }


    @Override
    public void addPLC(PlcConnection plc) {
        repositoryPLC.save(plc);
    }

    @Override
    public Optional<PlcConnection> getByIdPLC(long id) {
        return repositoryPLC.findById(id);
    }

    @Override
    public List<PlcConnection> allPLC() {
        return repositoryPLC.findAll();
    }

    @Override
    public boolean deletePLC(long id) {
        repositoryPLC.deleteById(id);
        return true;
    }
}
