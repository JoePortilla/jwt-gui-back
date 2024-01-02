package co.joeportilla.jwtguiback.config;

import co.joeportilla.jwtguiback.dto.UserDto;

import co.joeportilla.jwtguiback.repository.UserRepository;
import co.joeportilla.jwtguiback.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;

/**
 * Provider class which will create and validate the JWT
 */
@RequiredArgsConstructor
@Component
public class UserAuthenticationProvider {
    private final UserService userService;

    @Value("${security.jwt.secret.key}")
    private String secretKey;

    /**
     * Encode in Base64 the secret key that has been injected into the secretKey property. The reason for doing this
     * is to hide the secret key in its original form in the memory of the virtual machine (JVM).
     */
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /**
     * Method to create the token
     *
     * @param user
     * @return
     */
    public String createToken(UserDto user) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000); // 1 hour

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                  // Store the login
                  .withSubject(user.getLogin())
                  // Store the created date
                  .withIssuedAt(now)
                  // Define the validity date
                  .withExpiresAt(validity)
                  // Custom claims
                  .withClaim("firstName", user.getFirstName())
                  .withClaim("lastName", user.getLastName())
                  // Sign with secret key
                  .sign(algorithm);
    }

    /**
     * Method to validate the token
     *
     * @param token Token
     */
    public Authentication validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm)
                                  .build();

        DecodedJWT decoded = verifier.verify(token);

        // Use the information in the JWT to create a user DTO
        UserDto user = UserDto.builder()
                              .login(decoded.getSubject())
                              .firstName(decoded.getClaim("firstName").asString())
                              .lastName(decoded.getClaim("lastName").asString())
                              .build();

        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }

    public Authentication validateTokenStrongly(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm)
                                  .build();

        DecodedJWT decoded = verifier.verify(token);

        UserDto user = userService.findByLogin(decoded.getSubject());

        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }
}
