package com.gec.ecommerce.controller;

import com.gec.ecommerce.bases.BaseController;
import com.gec.ecommerce.bases.BaseService;
import com.gec.ecommerce.domain.User;
import com.gec.ecommerce.dto.UserShallowDto;
import com.gec.ecommerce.dto.request.UserRequest;
import com.gec.ecommerce.dto.response.UserResponse;
import com.gec.ecommerce.filter.UserFilter;
import com.gec.ecommerce.mapper.UserMapper;
import com.gec.ecommerce.service.UserService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController<User, UserFilter, UserShallowDto, UserRequest, UserResponse, UserService> {

    private final UserMapper userMapper;

    private final UserService userService;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;

        this.userMapper = userMapper;
    }

    @Override
    protected UserService getEntityService() {
        return this.userService;
    }

    @Override
    public ResponseEntity<UserResponse> findEntityById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<UserResponse> createNew(UserRequest userRequest) throws ServiceException {
        return null;
    }

    @Override
    public ResponseEntity<UserResponse> update(UserRequest userRequest, Long id) throws ServiceException {
        return null;
    }
}


