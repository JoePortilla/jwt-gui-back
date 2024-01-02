package co.joeportilla.jwtguiback.controller;

import co.joeportilla.jwtguiback.config.UserAuthenticationProvider;
import co.joeportilla.jwtguiback.dto.CredentialsDto;
import co.joeportilla.jwtguiback.dto.SignUpDto;
import co.joeportilla.jwtguiback.dto.UserDto;
import co.joeportilla.jwtguiback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @PostMapping("login")
    public ResponseEntity<UserDto> login(@RequestBody CredentialsDto credentialsDto) {
        UserDto user = userService.login(credentialsDto);
        user.setPassword(userAuthenticationProvider.createToken(user));
        return ResponseEntity.ok(user);
    }

    @PostMapping("register")
    public ResponseEntity<UserDto> register(@RequestBody SignUpDto signUpDto) {
        UserDto user = userService.register(signUpDto);
        user.setPassword(userAuthenticationProvider.createToken(user));
        return ResponseEntity.created(URI.create("/users/" + user.getId()))
                             .body(user);
    }
}
