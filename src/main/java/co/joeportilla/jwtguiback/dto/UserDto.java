package co.joeportilla.jwtguiback.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
/**
 * This is a class because I will use it in the reception and in the transmission.
 * And not all the fields will be set all together
 */
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
}
