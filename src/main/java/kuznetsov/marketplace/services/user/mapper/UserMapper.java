package kuznetsov.marketplace.services.user.mapper;

import kuznetsov.marketplace.domain.user.User;
import kuznetsov.marketplace.services.user.dto.UserDto;

public interface UserMapper {

  default UserDto toUserDto(User user) {
    return UserDto.builder()
        .email(user.getEmail())
        .isEmailConfirmed(user.isEmailConfirmed())
        .isBanned(user.isBanned())
        .build();
  }

}
