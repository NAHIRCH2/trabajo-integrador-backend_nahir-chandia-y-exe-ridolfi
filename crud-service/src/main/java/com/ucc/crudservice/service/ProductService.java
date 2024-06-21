package com.ucc.crudservice.service;
import com.ucc.crudservice.model.Product;
import com.ucc.crudservice.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    //metodo para obtener  todos los productos
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    // metodo para crear un producto
    public ResponseEntity<Object> addProduct(Product product) {
        productRepository.save(product);
        return new ResponseEntity<>("producto creado", HttpStatus.OK);
    }
    // metodo para borrar un producto
    public ResponseEntity<Object>  deleteProduct(Long id) {
        productRepository.deleteById(id);
        return new ResponseEntity<>("producto borrado", HttpStatus.OK);
    }
    // metodo para modificar un producto
    public ResponseEntity<Object> modifyProduct(Long id, Product product) {
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isPresent()){
        Product newproduct = productOptional.get() ;
        newproduct.setSku(product.getSku());
        newproduct.setName(product.getName());
        newproduct.setDescription(product.getDescription());
        newproduct.setPrice(product.getPrice());
        newproduct.setStatus(product.getStatus());
        productRepository.save(newproduct);
        return new ResponseEntity<>("producto modificado", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("producto no encotrado mediante el id ", HttpStatus.NOT_FOUND);
        }
    }
    public List<String> getSKus() {
   return productRepository.findAll().stream()
          .map(Product::getSku).toList();
    }
    }
