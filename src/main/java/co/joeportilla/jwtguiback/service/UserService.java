package co.joeportilla.jwtguiback.service;

import co.joeportilla.jwtguiback.dto.CredentialsDto;
import co.joeportilla.jwtguiback.dto.SignUpDto;
import co.joeportilla.jwtguiback.dto.UserDto;

public interface UserService {
    UserDto login(CredentialsDto credentialsDto);

    UserDto findByLogin(String login);

    UserDto register(SignUpDto signUpDto);
}
