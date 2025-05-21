package com.gec.ecommerce.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequest(

        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
        String name,

        @NotBlank(message = "A descrição é obrigatória")
        @Size(min = 5, max = 255, message = "A descrição deve ter entre 5 e 255 caracteres")
        String description,

        @NotNull(message = "O preço é obrigatório")
        @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero")
        BigDecimal price,

        @Min(value = 0, message = "A quantidade não pode ser negativa")
        int quantity,

        boolean active
        ){}
