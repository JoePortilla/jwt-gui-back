package co.joeportilla.jwtguiback.dto;

public record SignUpDto(String firstName,
                        String lastName,
                        String login,
                        char[] password) {
}
