package com.svetikov.telegrambot.model;

import com.sourceforge.snap7.moka7.S7;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import si.trina.moka7.live.PLC;
import si.trina.moka7.live.PLCListener;

import javax.persistence.*;

import java.util.concurrent.TimeUnit;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
//@Builder
@Entity
@Table(name = "plc_connection")
public class PlcConnection {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idPLC;
//    @Column(name = "plc_")
//    private PLC plc;
    private String plcName;
    private String plcIPAdr;
    private int plcLengthRead;
    private int plcNumberDataBlockRead;
    private int plcLengthWrite;
    private int plcNumberDataBlockWrite;
    private int plcRack;
    private int plcSlot;

    private boolean status = false;

    public PlcConnection(String plcName, String plcIPAdr, int plcLengthRead, int plcNumberDataBlockRead,
                         int plcLengthWrite, int plcNumberDataBlockWrite, int plcRack, int plcSlot) {
        this.plcName = plcName;
        this.plcIPAdr = plcIPAdr;
        this.plcLengthRead = plcLengthRead;
        this.plcLengthWrite = plcLengthWrite;
        this.plcNumberDataBlockRead = plcNumberDataBlockRead;
        this.plcNumberDataBlockWrite = plcNumberDataBlockWrite;
        this.plcRack = plcRack;
        this.plcSlot = plcSlot;

    }


//    public void initPLC() throws Exception {
//
//        log.info("data " + plcName + " " + plcIPAdr);
//
//        this.plc = new PLC(
//                this.plcName,
//                this.plcIPAdr,
//                this.plcLengthRead,
//                this.plcLengthWrite,
//                this.plcNumberDataBlockRead,
//                this.plcNumberDataBlockWrite,
//                new double[]{},
//                this.plcRack,
//                this.plcSlot,
//                S7.S7AreaDB,
//                S7.S7AreaDB);
//
//
//        PLCListenerImpl plcListener = new PLCListenerImpl();
//        plc.listeners.add(plcListener);
//        Thread plcFlow = new Thread(plc);
//        plcFlow.start();
//        TimeUnit.SECONDS.sleep(5);
//        status = plc.connected;
//        log.info(plc.connected + "  /  " + status);
//
//
//    }
//
//    private class PLCListenerImpl implements PLCListener {
//        @Override
//        public void PLCBitChanged(int address, int pos, boolean val, String plcName) {
//            switch (address) {
//                case 0:
//                    switch (pos) {
//                        case 1:
//                            System.out.println("Bit at address 0.1 of PLC " + plcName + " changed to: " + val);
//                    }
//            }
//        }
//    }
//
//
//    public PLC getPlc() {
//        return plc;
//    }
}
