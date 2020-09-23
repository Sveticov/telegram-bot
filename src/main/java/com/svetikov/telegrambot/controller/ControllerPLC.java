package com.svetikov.telegrambot.controller;

import com.svetikov.telegrambot.model.PlcConnection;
import com.svetikov.telegrambot.service.ServicePLC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plc")
public class ControllerPLC {

    private final ServicePLC servicePLC;

    public ControllerPLC(ServicePLC servicePLC) {
        this.servicePLC = servicePLC;
    }

    @PostMapping("/addplc")
    public ResponseEntity<PlcConnection> addPLC(@RequestBody PlcConnection plc) {
        servicePLC.addPLC(plc);
        return ResponseEntity.ok(plc);
    }

    @GetMapping("/getplc/{id}")
    public ResponseEntity<PlcConnection> getByIdPLC(@PathVariable long id) {
        PlcConnection plc = servicePLC.getByIdPLC(id).get();
        return ResponseEntity.ok(plc);
    }

    @GetMapping("/allplc")
    public ResponseEntity<List<PlcConnection>> allPLC() {

        return ResponseEntity.ok(servicePLC.allPLC());
    }

    @DeleteMapping("/deleteplc/{id}")
    public ResponseEntity<Boolean> deletePLC(@PathVariable long id) {
        return ResponseEntity.ok(servicePLC.deletePLC(id));
    }
}
