package com.svetikov.telegrambot.service;

import com.svetikov.telegrambot.model.PlcConnection;
import si.trina.moka7.live.PLC;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ServicePLC {
    void addPLC(PlcConnection plc);
    Optional<PlcConnection> getByIdPLC(long id);
    List<PlcConnection> allPLC();
    boolean deletePLC(long id);
    boolean initPLC(long id) throws InterruptedException;
    void addListPLC(List<PlcConnection> list);
    Map<String, PLC> getMapPLC();
}
