package com.gec.ecommerce.repository;

import com.gec.ecommerce.bases.BaseRepository;
import com.gec.ecommerce.domain.Cart;
import com.gec.ecommerce.domain.Order;
import com.gec.ecommerce.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends BaseRepository<Order> {

    List<Order> findByUser(User user);

    List<Order> findByStatus(String status);

    Optional<Order> findByTxid(String txid);

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId ORDER BY o.createdAt DESC")
    List<Order> findByUserIdOrderByDataCriacaoDesc(@Param("userId") Long userId);

    Optional<Order> findByUserId(Long userId);

    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);
}
