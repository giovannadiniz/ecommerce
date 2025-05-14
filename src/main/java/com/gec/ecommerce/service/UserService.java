package com.gec.ecommerce.service;

import com.gec.ecommerce.bases.BaseService;
import com.gec.ecommerce.domain.Coupon;
import com.gec.ecommerce.domain.User;
import com.gec.ecommerce.filter.UserFilter;
import com.gec.ecommerce.mapper.CouponMapper;
import com.gec.ecommerce.mapper.UserMapper;
import com.gec.ecommerce.repository.CouponRepository;
import com.gec.ecommerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


@Service
public class UserService extends BaseService<User, UserFilter> {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {

        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public JpaRepository<User, Long> getRepository() {

        return userRepository;
    }

    @Transactional
    @Override
    public User saveWithReturn(User user) {

        return getRepository().save(user);
    }

    @Override
    public Page<User> findAll(int page, int size, UserFilter userFilter) {
        return null;
    }
}
