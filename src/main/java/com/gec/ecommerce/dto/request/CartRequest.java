package com.gec.ecommerce.dto.request;

import com.gec.ecommerce.domain.User;

import java.math.BigDecimal;

public record CartRequest (
        User userId
) {}