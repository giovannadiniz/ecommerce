package com.gec.ecommerce.controller;

import com.gec.ecommerce.bases.BaseController;
import com.gec.ecommerce.bases.BaseService;
import com.gec.ecommerce.domain.User;
import com.gec.ecommerce.dto.request.UserRequest;
import com.gec.ecommerce.dto.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController<User, Long> {

    public UserController(BaseService<User, Long, UserRequest, UserResponse> service) {
        super(service);
    }

    @Override
    public ResponseEntity<User> findById(Long id) {
        return super.findById(id);
    }

    @Override
    public ResponseEntity<User> create(User newUser) {
        return super.create(newUser);
    }

    @Override
    public ResponseEntity<User> update(Long id, User user) {
        return super.update(id, user);
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        return super.delete(id);
    }
}


