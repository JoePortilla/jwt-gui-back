package co.joeportilla.jwtguiback.mapper;

import co.joeportilla.jwtguiback.dto.SignUpDto;
import co.joeportilla.jwtguiback.dto.UserDto;
import co.joeportilla.jwtguiback.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // User toUser(UserDto userDto);

    UserDto toUserDto(User user);

    // List<UserDto> toUsersDto(List<User> users);

    /**
     * Ignore the password because it has different format.
     * In record SignUpDto -> char[] password
     * In class User -> String password
     */
    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto signUpDto);
}
