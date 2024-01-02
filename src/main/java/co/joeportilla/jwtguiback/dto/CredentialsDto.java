package co.joeportilla.jwtguiback.dto;

/**
 * A record because the credentials DTO is just for reception
 * Records are immutable.
 * I won´t edit the content field by field, because there is no transmission
 *
 * @param login
 * @param password
 */
public record CredentialsDto(String login, char[] password) {
}
