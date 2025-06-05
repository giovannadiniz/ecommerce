package com.gec.ecommerce.repository;

import com.gec.ecommerce.bases.BaseRepository;
import com.gec.ecommerce.domain.Cart;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends BaseRepository<Cart> {

    Optional<Cart> findByUserId(Long userId);
    void deleteByUserId(Long userId);// Adicione este método
}