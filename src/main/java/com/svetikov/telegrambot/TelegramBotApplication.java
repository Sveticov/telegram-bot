package com.svetikov.telegrambot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.telegram.telegrambots.ApiContextInitializer;
import si.trina.moka7.live.PLC;
@Slf4j
@SpringBootApplication
@EnableScheduling
public class TelegramBotApplication implements CommandLineRunner {

    @Autowired
    PlcConnection plcConnection;

    public static void main(String[] args) {
        ApiContextInitializer.init();

        SpringApplication.run(TelegramBotApplication.class, args);

    }


    @Override
    public void run(String... args) throws Exception {

        plcConnection = PlcConnection.builder()
                .plcName("plc")
                .plcIPAdr("172.20.255.200")
                .plcLengthRead(10)
                .plcNumberDataBlockRead(2)
                .plcLengthWrite(10)
                .plcNumberDataBlockWrite(3)
                .plcRack(0)
                .plcSlot(2)
                .build();

        plcConnection.initPLC();



    }

    @Scheduled(initialDelay = 1000,fixedDelay = 5000)
    void showData() throws Exception {
        plcConnection.plcStatusConnect();

    }
}
