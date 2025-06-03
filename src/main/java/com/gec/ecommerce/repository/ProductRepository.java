package com.gec.ecommerce.repository;

import com.gec.ecommerce.bases.BaseRepository;
import com.gec.ecommerce.domain.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends BaseRepository<Product> {
}
