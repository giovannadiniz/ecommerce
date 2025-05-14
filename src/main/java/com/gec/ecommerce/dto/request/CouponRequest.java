package com.gec.ecommerce.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CouponRequest(
        @NotBlank(message = "O código do cupom é obrigatório")
        @Size(min = 5, max = 20, message = "O código deve ter entre 5 e 20 caracteres")
        String code,

        @NotNull(message = "O valor do cupom é obrigatório")
        @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
        @DecimalMax(value = "1000.00", message = "O valor máximo permitido é 1000.00")
        BigDecimal discount,

        @NotNull(message = "A data de criação é obrigatória")
        @PastOrPresent(message = "A data de criação não pode ser futura")
        LocalDateTime createdAt,

        @NotNull(message = "A data de validade é obrigatória")
        @Future(message = "A data de validade deve ser futura")
        LocalDateTime expirationDate)
        {}
