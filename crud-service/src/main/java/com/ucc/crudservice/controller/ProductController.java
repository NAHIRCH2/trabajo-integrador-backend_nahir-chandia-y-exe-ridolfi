package com.ucc.crudservice.controller;

import com.ucc.crudservice.model.Product;
import com.ucc.crudservice.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/products")
@RequiredArgsConstructor
public class ProductController {
    private  final   ProductService productService;
    // METODO  para obtener los productos
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getProducts() {
       return  this.productService.getProducts();
    }

    // metodo para crear un producto
  @PostMapping
  @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> newProduct(@Valid  @RequestBody Product product, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<String> errors =bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            return  new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
        }
        return this.productService.addProduct(product);
  }

    // metodo para borrar un producto
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public  ResponseEntity<Object>  deleteProduct(@PathVariable("id") Long id){
         return  this.productService.deleteProduct(id);
    }

    // metodo para modificar un producto
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public  ResponseEntity<Object>  modifyProduct(@Valid @PathVariable("id") Long id ,@RequestBody Product product,BindingResult bindingResult){

        return this.productService.modifyProduct(id,product);
    }

}

