package com.svetikov.telegrambot.controller;

import com.svetikov.telegrambot.model.AddressData;
import com.svetikov.telegrambot.service.ServiceAddressData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class ControllerAddressData {
    private final ServiceAddressData serviceAddressData;

    public ControllerAddressData(ServiceAddressData serviceAddressData) {
        this.serviceAddressData = serviceAddressData;
    }

    @PostMapping("/addaddress")
    public ResponseEntity<AddressData> addAddress(@RequestBody AddressData address) {
        serviceAddressData.addAddress(address);
        return ResponseEntity.ok(address);
    }
    @GetMapping("/getaddress/{id}")
    public ResponseEntity<AddressData> getAddressId(@PathVariable long id){
        return ResponseEntity.ok(serviceAddressData.getByIdAddressData(id).get());
    }
    @GetMapping("/alladdress")
    public ResponseEntity<List<AddressData>> allAddress(){
        return ResponseEntity.ok(serviceAddressData.allAddressData());
    }
    @DeleteMapping("/deleteaddrerss/{id}")
    public ResponseEntity<Boolean> deleteAddress(@PathVariable long id){
        return ResponseEntity.ok(serviceAddressData.deleteAddressData(id));
    }
}
