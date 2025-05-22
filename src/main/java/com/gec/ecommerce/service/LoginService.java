package com.gec.ecommerce.service;

import com.gec.ecommerce.bases.BaseService;
import com.gec.ecommerce.domain.User;
import com.gec.ecommerce.dto.request.LoginRequest;
import com.gec.ecommerce.dto.response.LoginResponse;
import com.gec.ecommerce.filter.UserFilter;
import com.gec.ecommerce.mapper.UserMapper;
import com.gec.ecommerce.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public class LoginService extends BaseService <User, UserFilter>{

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public LoginService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
//        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public JpaRepository<User, Long> getRepository() {
        return userRepository;
    }

    @Override
    public Page<User> findAll(int page, int size, UserFilter userFilter) {
        // Se não usar paginação/filtragem aqui, pode lançar UnsupportedOperationException
        throw new UnsupportedOperationException("Operação não suportada neste serviço.");
    }

//    public LoginResponse login(LoginRequest request) {
//        User user = userRepository.findByEmail(request.getEmail())
//                .orElseThrow(() -> new RuntimeException("Email ou senha inválidos"));
//
//        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//            throw new RuntimeException("Email ou senha inválidos");
//        }
//
//        // Aqui você pode gerar um token JWT se desejar
//        return userMapper.entityToResponse(user);
//    }
}
