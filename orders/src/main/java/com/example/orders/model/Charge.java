package com.example.orders.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name ="Charge")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Charge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;

    @NotBlank(message = "El código SKU no debe estar vacío")
    private String sku;

    @NotBlank(message = "El nombre del cliente es obligatorio")
    private String customer;

    @NotBlank(message = "La dirección de entrega es obligatoria")
    private String DeliveryAddress;

    @NotBlank(message = "La cantidad no debe estar vacía")
    @DecimalMin(value = "0.00",message = "el valor deberia ser mayor o igual a cero")

    private String amount;
}