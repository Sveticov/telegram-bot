package com.svetikov.telegrambot;

import com.sun.xml.bind.v2.TODO;
import com.svetikov.telegrambot.model.AddressData;
import com.svetikov.telegrambot.model.PlcConnection;
import com.svetikov.telegrambot.model.TypeAddress;
import com.svetikov.telegrambot.service.ServiceAddressData;
import com.svetikov.telegrambot.service_telegram.TelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.telegram.telegrambots.ApiContextInitializer;

import java.security.SecureRandom;
import java.util.List;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class TelegramBotApplication implements CommandLineRunner {

    @Autowired
    PlcConnection plcConnection;
    @Autowired
    TelegramBot telegramBot;
    List<AddressData> addressData;
    List<AddressData> messageData;
    @Autowired
    ServiceAddressData serviceAddressData;

    int bufferShift = 0;
    int bufferShiftMessages = 0;
    String str = "";

    public static void main(String[] args) {
        ApiContextInitializer.init();

        SpringApplication.run(TelegramBotApplication.class, args);

    }


    @Override
    public void run(String... args) throws Exception {

//        addressData = List.of(new AddressData("position car 1 X :", true, TypeAddress.DINT, 0, 0),
//                new AddressData("position car 1 Z :", true, TypeAddress.DINT, 4, 0),
//                new AddressData("car 1 up :", true, TypeAddress.BOOL, 8, 0));
//
        messageData = List.of(
                new AddressData(1l, "error 0:", true, TypeAddress.BOOL, 8, 2),
                new AddressData(1l, "error 1:", true, TypeAddress.BOOL, 8, 3),
                new AddressData(1l, "error 2:", true, TypeAddress.BOOL, 8, 4),
                new AddressData(1l, "error 3:", true, TypeAddress.BOOL, 8, 5),
                new AddressData(1l, "error 4:", true, TypeAddress.BOOL, 8, 6),
                new AddressData(1l, "error 5:", true, TypeAddress.BOOL, 8, 7),
                new AddressData(1l, "error 6:", true, TypeAddress.BOOL, 9, 0),
                new AddressData(1l, "error 7:", true, TypeAddress.BOOL, 9, 1),
                new AddressData(1l, "error 8:", true, TypeAddress.BOOL, 9, 2),
                new AddressData(1l, "error 9:", true, TypeAddress.BOOL, 9, 3),
                new AddressData(1l, "error 10:", true, TypeAddress.BOOL, 9, 4),
                new AddressData(1l, "error 11:", true, TypeAddress.BOOL, 9, 5),
                new AddressData(1l, "error 12:", true, TypeAddress.BOOL, 9, 6),
                new AddressData(1l, "error 13:", true, TypeAddress.BOOL, 9, 7),
                new AddressData(1l, "error 14:", true, TypeAddress.BOOL, 10, 0),
                new AddressData(1l, "error 15:", true, TypeAddress.BOOL, 10, 1),
                new AddressData(1l, "error 16:", true, TypeAddress.BOOL, 10, 2)
        );
        serviceAddressData.addListAddress(messageData);
//        plcConnection = PlcConnection.builder()
//                .plcName("plc")
//                .plcIPAdr("172.20.255.200")
//                .plcLengthRead(12)
//                .plcNumberDataBlockRead(2)
//                .plcLengthWrite(12)
//                .plcNumberDataBlockWrite(3)
//                .plcRack(0)
//                .plcSlot(2)
//                .build();
//
//        plcConnection.initPLC();


    }

    @Scheduled(initialDelay = 5000, fixedDelay = 10000)
    void showData() throws Exception {
//TODO: 22.09.2020 call general method for scheduled all address
        //   messageInfo(); the

//        testSendPositionCar1();

    }

    private void testSendPositionCar1() {
        addressData.stream()
                .forEach(
                        m -> {
                            if (m.getTypeAddress().equals(TypeAddress.DINT))
                                try {
                                    telegramBot.SendMyMessage(m.getMessageAddress() + plcConnection.getPlc().getDInt(m.isStatusAddress(), m.getByteAddress()));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            else if (m.getTypeAddress().equals(TypeAddress.BOOL))
                                try {
                                    telegramBot.SendMyMessage(m.getMessageAddress() +
                                            plcConnection.getPlc().getBool(m.isStatusAddress(),
                                                    m.getByteAddress(),
                                                    m.getBiteAddress()));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            log.info(m.toString());
                        }
                );
    }

    public void messageInfo() {

        messageData.stream()
                .filter(message ->
                        {
                            try {
                                boolean status = statusMessage(message);
                                if (status & message.isStatusAddress()) {
                                    message.setStatusAddress(!status);
                                    return status;
                                }
                                if (!status & !message.isStatusAddress())
                                    message.setStatusAddress(!status);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return false;
                        }
                )
                .forEach(message -> {
                    log.info(message.getMessageAddress());
                    telegramBot.SendMyMessage(message.getMessageAddress());
                });

    }

    private boolean statusMessage(AddressData message) throws Exception {
        boolean status;
        status = plcConnection.getPlc().getBool(true,
                message.getByteAddress(),
                message.getBiteAddress());
        //   log.info("status " + status);
        return status;
    }
}
