package com.ucc.crudservice.service;

import com.ucc.crudservice.model.Product;
import com.ucc.crudservice.repositories.ProductRepository;
import org.junit.jupiter.api.Assertions;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductServiceTest {
 @MockBean
 private ProductRepository productRepository;
 @Autowired
 private ProductService productService;

 @BeforeEach
 void setUp() {
  MockitoAnnotations.openMocks(this);
 }


 @Test
 public void getProducts() {
  Product product = new Product(1L, "sku00103", "product1", "descripcion1", 100.0, true);
  List<Product> products = Collections.singletonList(product);
  when(productRepository.findAll()).thenReturn(products);
  List<Product> result = productService.getProducts();
  assertEquals(1, result.size());
  assertEquals("sku00103", result.get(0).getSku());
 }

 @Test
 public void testDeleteProduct() {
  Long productId = 1L;
  ResponseEntity<Object> response = productService.deleteProduct(productId);
  verify(productRepository, times(1)).deleteById(productId);
  assertEquals(HttpStatus.OK, response.getStatusCode());
  assertEquals("producto borrado", response.getBody());
 }

 @Test
 public void testCreateProduct() {
  Product product = new Product(1L, "sku00103", "product1", "descripcion1", 100.0, true);
  ResponseEntity<Object> response = productService.addProduct(product);
  verify(productRepository, times(1)).save(product);
  assertEquals(HttpStatus.OK, response.getStatusCode());
  assertEquals("producto creado", response.getBody());
 }

 @Test
 public void testModifyProduct() {
  Product product = new Product(1L, "sku00103", "product1", "descripcion1", 100.0, true);
  List<Product> products = Collections.singletonList(product);
  when(productRepository.findAll()).thenReturn(products);
  List<Product> result = productService.getProducts();
  result.get(0).setSku("sku00205");
  result.get(0).setName("product2");
  result.get(0).setDescription("description2");
  result.get(0).setPrice(200.0);
  result.get(0).setStatus(false);
  Product productupdated = result.get(0);
 assertEquals("sku00205",productupdated.getSku());
  assertEquals("product2",productupdated.getName());
  assertEquals("description2",productupdated.getDescription());
  assertEquals(200.0,productupdated.getPrice());
  assertEquals(false,productupdated.getStatus());
 }
}