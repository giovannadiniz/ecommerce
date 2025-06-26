package com.gec.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PixChargeRequest(
        @NotBlank
        String chave,

        @NotBlank
        String valor) {
}
