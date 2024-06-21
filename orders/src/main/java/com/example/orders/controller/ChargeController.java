package com.example.orders.controller;

import com.example.orders.service.ChargeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.orders.model.Charge;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/Charge")
@RequiredArgsConstructor
public class ChargeController {
    private final ChargeService chargeService;

    // Método para obtener órdenes
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Charge> getOrders() {
        return this.chargeService.getCharge();
    }

    // Método para crear orden
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> newCharge(@Valid @RequestBody Charge charge, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            // Manejo de errores
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        return chargeService.addCharge(charge);
    }
    // metodo para borrar una orden
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public  ResponseEntity<Object>  deleteChange(@PathVariable("id") Long id){
        return  this.chargeService.deleteChange(id);
    }


    // Método para actualizar orden
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateCharge(@PathVariable("id") Long id, @RequestBody Charge updateCharge){
        return this.chargeService.modifyCharge(id, updateCharge);
    }
}
