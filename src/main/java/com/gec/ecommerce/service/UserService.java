package com.gec.ecommerce.service;

import com.gec.ecommerce.bases.BasePaginatedResponse;
import com.gec.ecommerce.bases.BaseService;
import com.gec.ecommerce.domain.Coupon;
import com.gec.ecommerce.domain.User;
import com.gec.ecommerce.dto.CouponShallowDto;
import com.gec.ecommerce.dto.UserShallowDto;
import com.gec.ecommerce.dto.request.UserRequest;
import com.gec.ecommerce.dto.response.UserResponse;
import com.gec.ecommerce.exception.CouponNotFoundException;
import com.gec.ecommerce.filter.CouponFilter;
import com.gec.ecommerce.filter.UserFilter;
import com.gec.ecommerce.mapper.CouponMapper;
import com.gec.ecommerce.mapper.UserMapper;
import com.gec.ecommerce.repository.CouponRepository;
import com.gec.ecommerce.repository.UserRepository;
import jakarta.persistence.criteria.Predicate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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

    public BasePaginatedResponse<UserShallowDto> listAll(Integer page, Integer size, UserFilter userFilter, HttpServletRequest request){
        UriComponentsBuilder uri = UriComponentsBuilder.fromPath(request.getServletPath()).query(request.getQueryString());

        Page<User> userPage = findAll(page,size,userFilter);

        return new BasePaginatedResponse<>(userPage.map(userMapper::entityToShallowDto),uri);
    }


    @Override
    public Page<User> findAll(int page, int size, UserFilter userFilter) {
        Pageable pageable = PageRequest.ofSize(size).withPage(page);

        Specification<User> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (userFilter.username() != null)
                predicates.add(cb.equal(root.get("username"), userFilter.username()));
            if (userFilter.email() != null)
                predicates.add(cb.equal(root.get("email"), userFilter.email()));
            if (userFilter.phone() != null)
                predicates.add(cb.equal(root.get("phone"), userFilter.phone()));

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return userRepository.findAll(spec, pageable);
    }

    public UserResponse findUserById(Long id) {
        User user = findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.entityToResponse(user);
    }


    @Transactional
    public UserResponse update(Long id, UserRequest userRequest) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new CouponNotFoundException(id));

        var userUpdated = userMapper.requestToEntity(userRequest);

        BeanUtils.copyProperties(userUpdated, existingUser, "id");

        var updatedUser = userRepository.save(existingUser);

        return userMapper.entityToResponse(updatedUser);
    }

    @Transactional
    public void delete(Long id) {
        Optional<User> user = findById(id);
        user.ifPresentOrElse(userRepository::delete,
        () -> {
            throw new RuntimeException("User not found");}
        );
    }

}
