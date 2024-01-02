package co.joeportilla.jwtguiback.service;

import co.joeportilla.jwtguiback.dto.CredentialsDto;
import co.joeportilla.jwtguiback.dto.SignUpDto;
import co.joeportilla.jwtguiback.dto.UserDto;
import co.joeportilla.jwtguiback.entity.User;
import co.joeportilla.jwtguiback.exception.AppException;
import co.joeportilla.jwtguiback.mapper.UserMapper;
import co.joeportilla.jwtguiback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;


    @Override
    public UserDto login(CredentialsDto credentialsDto) {
        // Look by a user with the login
        User user = userRepository.findByLogin(credentialsDto.login())
                                  .orElseThrow(() -> new AppException("Unknown user",
                                                                      HttpStatus.NOT_FOUND));
        // Compare the given password with the password in the database
        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()),
                                    user.getPassword())) {
            // If its correct, I map the user from the db to a user dto
            return userMapper.toUserDto(user);
        }
        // if its not correct, throw exception
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    @Override
    public UserDto findByLogin(String login) {
        User user = userRepository.findByLogin(login)
                                  .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto register(SignUpDto signUpDto) {
        Optional<User> optionalUser = userRepository.findByLogin(signUpDto.login());
        if (optionalUser.isPresent()) {
            throw new AppException("Login Already exists", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.signUpToUser(signUpDto);

        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDto.password())));

        User savedUser = userRepository.save(user);

        return userMapper.toUserDto(savedUser);
    }
}
