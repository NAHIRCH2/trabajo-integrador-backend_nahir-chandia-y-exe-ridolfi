package com.example.orders.service;

import com.example.orders.model.Charge;
import com.example.orders.repositories.ChargeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChargeService {
    private final ChargeRepository chargeRepository;
    private  final RestTemplate restTemplate;
    // Método para obtener órdenes
    public List<Charge> getCharge() {return chargeRepository.findAll();}
    //metodo PARA CREAR ORDENES
    public ResponseEntity<Object> addCharge (Charge charge) {
        String[]skus = restTemplate.getForObject("http://localhost:8083/api/products/sku",String[].class);
        List<String> skuslist = Arrays.stream(skus).toList();
        boolean skuExist = skuslist.stream()
                .anyMatch(sku -> sku.equalsIgnoreCase(charge.getSku()));
        if (skuExist){
            chargeRepository.save(charge);
            return new ResponseEntity<>("Orden creada", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("el sku del producto no existe  y/o la api del producto no esta levantada", HttpStatus.NOT_FOUND);
        }


    }
    // metodo para borrar un producto
    public ResponseEntity<Object>  deleteChange(Long id) {
        chargeRepository.deleteById(id);
        return new ResponseEntity<>("orden borrada", HttpStatus.OK);
    }
    // metodos para modificar
    public ResponseEntity<Object> modifyCharge(Long id, Charge charge) {
        String[]skus = restTemplate.getForObject("http://localhost:8083/api/products/sku",String[].class);
        List<String> skuslist = Arrays.stream(skus).toList();
        boolean skuExist = skuslist.stream()
                .anyMatch(sku -> sku.equalsIgnoreCase(charge.getSku()));
        if (skuExist) {
            Optional<Charge> chargeOptional = chargeRepository.findById(id);
            if (chargeOptional.isPresent()) {
                Charge newcharge = chargeOptional.get();
                newcharge.setSku(charge.getSku());
                newcharge.setCustomer(charge.getCustomer());
                newcharge.setAmount(charge.getAmount());
                newcharge.setDeliveryAddress(charge.getDeliveryAddress());
                chargeRepository.save(newcharge);

                return new ResponseEntity<>("Orden modificado", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Orden no encotrado mediante el id ", HttpStatus.NOT_FOUND);
            }
        }else {
            return new ResponseEntity<>("el sku del producto no existe  y/o la api del producto no esta levantada", HttpStatus.NOT_FOUND);
        }
    }
}
