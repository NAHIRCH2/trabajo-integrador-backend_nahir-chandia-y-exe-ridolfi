package com.example.orders.service;

import com.example.orders.model.Charge;
import com.example.orders.repositories.ChargeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ChargeServiceTest {

    @MockBean
    private ChargeRepository chargeRepository;

    @Autowired
    private ChargeService chargeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getCharges() {
        Charge charge = new Charge(1L, "sku00103", "product1", "descripcion1", "adress1");
        List<Charge> charges = Collections.singletonList(charge);
        when(chargeRepository.findAll()).thenReturn(charges);
        List<Charge> result = chargeService.getCharge();
        assertEquals(1, result.size());
        assertEquals("sku00103", result.get(0).getSku());
    }

    @Test
    public void testDeleteCharge() {  // Corrected method name
        Long chargeId = 1L;
        ResponseEntity<Object> response = chargeService.deleteChange(chargeId);  // Corrected method call
        verify(chargeRepository, times(1)).deleteById(chargeId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("orden borrada", response.getBody());
    }

    @Test
    public void testCreateCharge() {
        Charge charge = new Charge(1L, "sku001044", "product1444", "descripcion144", "adress1444");
        ResponseEntity<Object> response = chargeService.addCharge(charge);
        verify(chargeRepository, times(1)).save(charge);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("orden creada", response.getBody().toString().toLowerCase());

    }

    @Test
    public void testModifyCharge() {  // Updated test name for clarity
        Charge charge = new Charge(1L, "sku00103", "product1", "descripcion1", "adress1");
        when(chargeRepository.findById(1L)).thenReturn(java.util.Optional.of(charge));  // Mock findById
        charge.setSku("sku00205");
        charge.setCustomer("product2");
        charge.setAmount("description2");
        charge.setDeliveryAddress("adress1");

        chargeService.modifyCharge(1L, charge);  // Assuming updateCharge method exists in service
        verify(chargeRepository, times(1)).save(charge);

        assertEquals("sku00205", charge.getSku());
        assertEquals("product2", charge.getCustomer());
        assertEquals("description2", charge.getAmount());
        assertEquals("adress1", charge.getDeliveryAddress());
    }
}
