package com.borlok.mvcSpine.dto;

import com.borlok.mvcSpine.model.User;
import com.borlok.mvcSpine.model.UserType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class UserDto {

    private Long id;
    private String phoneNumber;
    private Long customerId;
    private String firstName;
    private String lastName;
    private String userType;
    private String avatarUrl;

    public static UserDto fromUser(User user) {

        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .phoneNumber(user.getPhoneNumber())
                .customerId(user.getCustomerId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userType(user.getUserType().name())
                .avatarUrl(user.getAvatarUrl())
                .build();


        return userDto;
    }

    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setPhoneNumber(phoneNumber);
        user.setCustomerId(customerId);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserType(UserType.valueOf(userType));
        user.setAvatarUrl(avatarUrl);
        return user;
    }

    public static List<UserDto> fromUsersList(List<User> users) {
        List<UserDto> result = new ArrayList<>();
        users.forEach(user -> {
            result.add(UserDto.fromUser(user));
        });

        return result;
    }
}
