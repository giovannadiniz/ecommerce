package com.gec.ecommerce.repository;

import com.gec.ecommerce.bases.BaseRepository;
import com.gec.ecommerce.domain.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User> {

    User findByUsername(String username);
}
