package com.gec.ecommerce.controller;

import com.gec.ecommerce.bases.BaseController;
import com.gec.ecommerce.bases.BasePaginatedResponse;
import com.gec.ecommerce.domain.User;
import com.gec.ecommerce.dto.UserShallowDto;
import com.gec.ecommerce.dto.request.UserRequest;
import com.gec.ecommerce.dto.response.UserResponse;
import com.gec.ecommerce.filter.UserFilter;
import com.gec.ecommerce.mapper.UserMapper;
import com.gec.ecommerce.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
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
    public ResponseEntity<BasePaginatedResponse<UserShallowDto>> listAll(Integer page, Integer size, UserFilter userFilter, HttpServletRequest request) {

        return ResponseEntity.ok().body(this.userService.listAll(page,size,userFilter,request));
    }

    @Override
    public ResponseEntity<UserResponse> findEntityById(Long id) {
        return ResponseEntity.ok().body(this.userService.findUserById(id));
    }

    @Override
    public ResponseEntity<UserResponse> createNew(@Valid @RequestBody UserRequest userRequest){
        User user = userService.saveWithReturn(userMapper.requestToEntity(userRequest));
        return ResponseEntity.ok().body(userMapper.entityToResponse(user));
    }

    @Override
    public ResponseEntity<UserResponse> update(@Valid @RequestBody UserRequest userRequest, Long id) {
        UserResponse updatedUser = userService.update(id, userRequest);
        return ResponseEntity.ok(updatedUser);
    }

    public ResponseEntity<String> delete(Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}


