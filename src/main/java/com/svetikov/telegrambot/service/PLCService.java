package com.svetikov.telegrambot.service;

import com.sourceforge.snap7.moka7.S7;
import com.svetikov.telegrambot.model.PlcConnection;
import com.svetikov.telegrambot.repository.RepositoryPLC;
import com.svetikov.telegrambot.service_telegram.TelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import si.trina.moka7.live.PLC;
import si.trina.moka7.live.PLCListener;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class PLCService implements ServicePLC {

    private PLC plc;
    private boolean statusPLC = false;
    private PlcConnection plcConnection;
    private Map<String, PLC> mapList;

    private final RepositoryPLC repositoryPLC;
    private final ServiceAddressData serviceAddressData;
    private final TelegramBot telegramBot;

    public PLCService(RepositoryPLC repositoryPLC, ServiceAddressData serviceAddressData, TelegramBot telegramBot) {
        this.repositoryPLC = repositoryPLC;
        this.serviceAddressData = serviceAddressData;
        this.telegramBot = telegramBot;
        mapList = new HashMap<>();
    }

    @Override
    public Map<String, PLC> getMapPLC() {
        return mapList;
    }


    @Override
    public void addPLC(PlcConnection plc) {
        log.info("plc " + plc.toString());
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

    @Override
    public boolean initPLC(long id) throws InterruptedException {


        this.plcConnection = repositoryPLC.findById(id).get();
        this.plc = new PLC(
                this.plcConnection.getPlcName(),
                this.plcConnection.getPlcIPAdr(),
                this.plcConnection.getPlcLengthRead(),
                this.plcConnection.getPlcLengthWrite(),
                this.plcConnection.getPlcNumberDataBlockRead(),
                this.plcConnection.getPlcNumberDataBlockWrite(),
                new double[]{},
                this.plcConnection.getPlcRack(),
                this.plcConnection.getPlcSlot(),
                S7.S7AreaDB,
                S7.S7AreaDB);

        PLCListenerImpl plcListener = new PLCListenerImpl();
        plc.listeners.add(plcListener);
        Thread plcFlow = new Thread(plc);
        plcFlow.start();
        TimeUnit.SECONDS.sleep(5);
        this.statusPLC = plc.connected;
        this.plcConnection.setStatus(this.statusPLC);//todo new 25.09.2020 set status connect plc
        repositoryPLC.save(this.plcConnection);
        mapList.put(plcConnection.getPlcName(), plc);
        log.info("plc is connected " + this.statusPLC);

        //Todo: 24.09.2020 read AddressData
        // readeAddressData();

        return statusPLC;
    }

    @Override
    public void addListPLC(List<PlcConnection> list) {
        repositoryPLC.saveAll(list);
    }

    private class PLCListenerImpl implements PLCListener {
        @Override
        public void PLCBitChanged(int address, int pos, boolean val, String plcName) {
            switch (address) {
                case 0:
                    switch (pos) {
                        case 1:
                            System.out.println("Bit at address 0.1 of PLC " + plcName + " changed to: " + val);
                    }
            }
        }
    }

//    private void readeAddressData() {
//        serviceAddressData.allAddressData().stream()
//                .filter(address -> {
//                    boolean checkName = address.getPlcNameAddress().equals(this.plcConnection.getPlcName());
//                    //  log.info(address.toString());
//                    return checkName;
//                })
//                .filter(message ->
//                        {
//                            log.info("message filter " + plcConnection.getPlcName());
//                            try {
//                                boolean status = statusMessage(message);
//                                //     log.info("status " + status);
//                                if (status & message.isStatusAddress()) {
//                                    log.info("Befo: " + message.isStatusAddress() + " " + status);
//                                    message.setStatusAddress(false);
//                                    serviceAddressData.addAddress(message);
//                                    log.info("After: " + message.isStatusAddress() + " " + status);
//                                    return status;
//                                }
//                                if (!status & !message.isStatusAddress()) {
//                                    message.setStatusAddress(true);
//                                    serviceAddressData.addAddress(message);
//                                    log.info(message.isStatusAddress() + " " + status);
//                                }
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                            return false;
//                        }
//                )
//                .forEach(message -> {
//                    //    log.info(message.getMessageAddress());
//                    telegramBot.SendMyMessage(message.getMessageAddress());
//                });
//    }
//
//    private boolean statusMessage(AddressData message) throws Exception {
//        boolean status;
//        // log.info("status plc connect " + plc.connected);
//        status = plc.getBool(true,
//                message.getByteAddress(),
//                message.getBiteAddress());
//        // log.info("status plc " + status);
//        return status;
//    }


}
