package com.gec.ecommerce.service;

import com.gec.ecommerce.bases.BaseService;
import com.gec.ecommerce.domain.User;
import com.gec.ecommerce.dto.request.UserRequest;
import com.gec.ecommerce.dto.response.UserResponse;
import com.gec.ecommerce.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService extends BaseService<User, Long, UserResponse, UserRequest> {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserResponse> findById(Long id) {
        return userRepository.findById(id)
                .map(this::toResponse);
    }

    @Override
    public UserResponse save(UserRequest userRequest) {
        User user = toEntity(userRequest);
        User savedUser = userRepository.save(user);
        return toResponse(savedUser);
    }

    @Override
    public UserResponse update(Long id, UserRequest userRequest) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        updateEntity(existingUser, userRequest);
        User updatedUser = userRepository.save(existingUser);
        return toResponse(updatedUser);
    }

    @Override
    public void delete(Long id) throws EntityNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    // Métodos auxiliares para conversão
//    private UserResponse toResponse(User user) {
//        UserResponse response = new UserResponse();
//        response.setId(user.getId());
//        response.setFullname()(user.getFullname());
//        response.setEmail(user.getEmail());
//        // Outros campos conforme necessário
//        return response;
//    }
//
//    private User toEntity(UserRequest request) {
//        User user = new User();
//        user.setName(request.getName());
//        user.setEmail(request.getEmail());
//        // Outros campos conforme necessário
//        return user;
//    }
//
//    private void updateEntity(User user, UserRequest request) {
//        user.setName(request.getName());
//        user.setEmail(request.getEmail());
//        // Outros campos conforme necessário
//    }
}
