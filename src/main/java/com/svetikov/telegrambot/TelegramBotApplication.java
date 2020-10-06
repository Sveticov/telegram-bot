package com.svetikov.telegrambot;

import com.svetikov.telegrambot.model.AddressData;
import com.svetikov.telegrambot.model.PlcConnection;
import com.svetikov.telegrambot.model.TypeAddress;
import com.svetikov.telegrambot.service.ServiceAddressData;
import com.svetikov.telegrambot.service.ServicePLC;
import com.svetikov.telegrambot.service_telegram.TelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.telegram.telegrambots.ApiContextInitializer;
import si.trina.moka7.live.PLC;

import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootApplication
//@EnableAsync
@EnableScheduling
public class TelegramBotApplication implements CommandLineRunner {

    //    @Autowired
//    PlcConnection plcConnection;
    @Autowired
    TelegramBot telegramBot;
    List<AddressData> addressData;
    List<AddressData> messageData;
    List<PlcConnection> plcConnections;
    PlcConnection plcConnection;
    PLC plcS7;
    @Autowired
    ServiceAddressData serviceAddressData;
    @Autowired
    ServicePLC servicePLC;

    int bufferShift = 0;
    int bufferShiftMessages = 0;
    String str = "";

    public static void main(String[] args) {
        ApiContextInitializer.init();

        SpringApplication.run(TelegramBotApplication.class, args);

    }


    @Override
    public void run(String... args) throws Exception {

        PlcConnection plcConnection1 = new PlcConnection("plc12", "172.20.255.200",
                3, 100, 1, 3, 0, 2);
        PlcConnection plcConnection2 = new PlcConnection("plc", "172.20.255.200",
                12, 2, 1, 3, 0, 2);

        plcConnections = List.of(plcConnection1, plcConnection2);

        messageData = List.of(
                new AddressData("plc", "error 0:", true, TypeAddress.BOOL, 8, 2),
                new AddressData("plc", "error 1:", true, TypeAddress.BOOL, 8, 3),
                new AddressData("plc", "error 2:", true, TypeAddress.BOOL, 8, 4),
                new AddressData("plc", "error 3:", true, TypeAddress.BOOL, 8, 5),
                new AddressData("plc", "error 4:", true, TypeAddress.BOOL, 8, 6),
                new AddressData("plc", "error 5:", true, TypeAddress.BOOL, 8, 7),
                new AddressData("plc", "error 6:", true, TypeAddress.BOOL, 9, 0),
                new AddressData("plc", "error 7:", true, TypeAddress.BOOL, 9, 1),
                new AddressData("plc", "error 8:", true, TypeAddress.BOOL, 9, 2),
                new AddressData("plc", "error 9:", true, TypeAddress.BOOL, 9, 3),
                new AddressData("plc", "error 10:", true, TypeAddress.BOOL, 9, 4),
                new AddressData("plc", "error 11:", true, TypeAddress.BOOL, 9, 5),
                new AddressData("plc", "error 12:", true, TypeAddress.BOOL, 9, 6),
                new AddressData("plc", "error 13:", true, TypeAddress.BOOL, 9, 7),
                new AddressData("plc", "error 14:", true, TypeAddress.BOOL, 10, 0),
                new AddressData("plc", "error 15:", true, TypeAddress.BOOL, 10, 1),
                new AddressData("plc", "error 16:", true, TypeAddress.BOOL, 10, 2),
                new AddressData("plc12", "error 1612:", true, TypeAddress.BOOL, 0, 0),
                new AddressData("plc12", "error 1712:", true, TypeAddress.BOOL, 0, 1),
                new AddressData("plc12", "error 11812:", true, TypeAddress.BOOL, 0, 2),
                new AddressData("plc12", "error 16112:", true, TypeAddress.BOOL, 0, 3),
                new AddressData("plc12", "error 16212:", true, TypeAddress.BOOL, 0, 4),
                new AddressData("plc12", "error 16312:", true, TypeAddress.BOOL, 0, 5),
                new AddressData("plc12", "error 16312:", true, TypeAddress.BOOL, 0, 6),
                new AddressData("plc12", "error 16312:", true, TypeAddress.BOOL, 0, 7),
                new AddressData("plc12", "error 16312:", true, TypeAddress.BOOL, 1, 0),
                new AddressData("plc12", "error 16312:", true, TypeAddress.BOOL, 1, 1),
                new AddressData("plc12", "error 16312:", true, TypeAddress.BOOL, 1, 2),
                new AddressData("plc12", "error 16312:", true, TypeAddress.BOOL, 1, 3),
                new AddressData("plc12", "error 16312:", true, TypeAddress.BOOL, 1, 4),
                new AddressData("plc12", "error 16312:", true, TypeAddress.BOOL, 1, 5),
                new AddressData("plc12", "error 16312:", true, TypeAddress.BOOL, 1, 6),
                new AddressData("plc12", "error 16312:", true, TypeAddress.BOOL, 1, 7)

        );
        serviceAddressData.addListAddress(messageData);
        servicePLC.addListPLC(plcConnections);


    }

    @Scheduled(initialDelay = 5000, fixedDelay = 5000)
    void showData() throws Exception {
//TODO: 25.09.2020 call general method for scheduled all address
        if (!servicePLC.allPLC().isEmpty() & !serviceAddressData.allAddressData().isEmpty()) {
            servicePLC.allPLC().stream()
                    .filter(plc -> plc.isStatus())
                    .forEach(plc_ -> {
                        serviceAddressData.allAddressData().stream()
                                .filter(address -> {
//                                    log.info("filter plc name");
//                                    log.info("name plc: "+address.getPlcNameAddress()+"  "+plc_.getPlcName());
                                    return address.getPlcNameAddress().equals(plc_.getPlcName());
                                })
                                .filter(address ->
                                        {
                                            // log.info("message filter " + plc_.getPlcName());
                                            try {
                                                boolean statusAddressActive = statusMessage(address);
                                             //   log.info("status " + statusAddressActive);
                                                if (statusAddressActive & address.isStatusAddress()) {
                                                  //  log.info("Befo: " + address.isStatusAddress() + " " + statusAddressActive);
                                                    address.setStatusAddress(false);
                                                    serviceAddressData.addAddress(address);
                                                  //  log.info("After: " + address.isStatusAddress() + " " + statusAddressActive);
                                                    return statusAddressActive;
                                                }
                                                if (!statusAddressActive & !address.isStatusAddress()) {
                                                    address.setStatusAddress(true);
                                                    serviceAddressData.addAddress(address);
                                               //     log.info(address.isStatusAddress() + " " + statusAddressActive);
                                                }

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            return false;
                                        }
                                )
                                .forEach(message -> {
                                    telegramBot.SendMyMessage(message.getMessageAddress());
                                    log.error("message " + message.getMessageAddress());
                                    });

                    });

        }
    }

    boolean statusMessage(AddressData addressData) throws Exception {
        boolean status;
        // log.info("status plc connect " + plc.connected);
        plcS7 = servicePLC.getMapPLC().entrySet().stream()
                .filter(plc -> {
                    //  log.info("plc conn: " + plc.getKey() + " " + addressData.getPlcNameAddress());
                    return plc.getKey().equals(addressData.getPlcNameAddress());
                })
                .map(Map.Entry::getValue)
                .findFirst()
                .get();//todo 25.09.2020 continiy
        // log.info("plcS7 " + plcS7.connected);

        status = plcS7.getBool(true,
                addressData.getByteAddress(),
                addressData.getBiteAddress());
        log.info("status plc " + status + " " + addressData.toString());
        return status;
    }



}
