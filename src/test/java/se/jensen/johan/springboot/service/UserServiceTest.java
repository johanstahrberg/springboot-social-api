package se.jensen.johan.springboot.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import se.jensen.johan.springboot.dto.UserRequestDto;
import se.jensen.johan.springboot.dto.UserResponseDto;
import se.jensen.johan.springboot.mapper.UserMapper;
import se.jensen.johan.springboot.model.User;
import se.jensen.johan.springboot.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserService userService;

    @Test
    void addUser_shouldSaveUser_whenHappyPath() {
        UserRequestDto dto = mock(UserRequestDto.class);

        User user = new User();
        user.setUsername("johan");
        user.setEmail("johan@mail.com");
        user.setPassword("plain");

        when(userMapper.fromDto(dto)).thenReturn(user);
        when(passwordEncoder.encode("plain")).thenReturn("hashed");
        when(userRepository.existsByUsernameOrEmail("johan", "johan@mail.com")).thenReturn(false);

        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponseDto result = userService.addUser(dto);

        assertNotNull(result);
        verify(passwordEncoder).encode("plain");
        verify(userRepository).save(user);
    }
}