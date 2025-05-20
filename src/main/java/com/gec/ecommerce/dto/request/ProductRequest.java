package com.gec.ecommerce.dto.request;

import jakarta.persistence.Column;

import java.math.BigDecimal;

public record ProductRequest(
        Long id,

        @jakarta.validation.constraints.NotBlank(message = "O nome é obrigatório")
        @jakarta.validation.constraints.Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
        String name,

        @jakarta.validation.constraints.NotBlank(message = "A descrição é obrigatória")
        @jakarta.validation.constraints.Size(min = 5, max = 255, message = "A descrição deve ter entre 5 e 255 caracteres")
        String description,

        @jakarta.validation.constraints.NotNull(message = "O preço é obrigatório")
        @jakarta.validation.constraints.DecimalMin(value = "0.01", message = "O preço deve ser maior que zero")
        BigDecimal price,

        @jakarta.validation.constraints.Min(value = 0, message = "A quantidade não pode ser negativa")
        int quantity,

        boolean active)
{}
