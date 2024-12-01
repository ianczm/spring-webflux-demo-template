package com.onboarding.demo.domain.users;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

import lombok.Builder;

@Builder(toBuilder = true)
public record UserDto(

    @Nullable
    @Null
    Long id,

    @NotBlank
    String name,

    @Email
    String email,

    @NotBlank
    @Size(min = 2, max = 2)
    String country

) {

    public static UserDto fromEntity(UserEntity userEntity) {
        return UserDto.builder()
            .id(userEntity.id)
            .name(userEntity.name)
            .email(userEntity.email)
            .country(userEntity.country)
            .build();
    }

    public static UserEntity toEntity(UserDto userDto) {
        return UserEntity.builder()
            .id(userDto.id)
            .name(userDto.name)
            .email(userDto.email)
            .country(userDto.country)
            .build();
    }
}
