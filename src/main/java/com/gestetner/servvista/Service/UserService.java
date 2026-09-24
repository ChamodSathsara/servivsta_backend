package com.gestetner.servvista.Service;

import com.gestetner.servvista.Dto.identity.CreateUserRequest;
import com.gestetner.servvista.Dto.identity.UserResponse;
import com.gestetner.servvista.Mapper.UserMapper;
import com.gestetner.servvista.Models.entity.identity.User;
import com.gestetner.servvista.Repositories.identity.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Locale;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse createUser(CreateUserRequest request) {
        String normalizedEmail = request.email().trim().toLowerCase(Locale.ROOT);
        if (userRepository.existsByEmailIgnoreCase(normalizedEmail)) {
            throw new IllegalStateException("A user with email '" + normalizedEmail + "' already exists");
        }

        String passwordHash = passwordEncoder.encode(request.password());
        User user = userMapper.toEntity(request, passwordHash, LocalDateTime.now());

        try {
            return userMapper.toResponse(userRepository.save(user));
        } catch (DataIntegrityViolationException exception) {
            throw new IllegalStateException(
                    "A user with the supplied unique details already exists", exception);
        }
    }
}
