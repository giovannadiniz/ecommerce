package com.gec.ecommerce.repository;

import com.gec.ecommerce.bases.BaseRepository;
import com.gec.ecommerce.domain.User;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User> {

    Optional<User> findByEmail(String email);

}
