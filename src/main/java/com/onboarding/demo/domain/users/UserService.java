package com.onboarding.demo.domain.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Flux<UserDto> findAll() {
        return userRepository.findAll()
            .map(UserDto::fromEntity);
    }

    public Mono<UserDto> findById(Long userId) {
        return userRepository.findById(userId)
            .map(UserDto::fromEntity);
    }

    public Mono<UserDto> create(UserDto userDto) {
        return Mono.just(userDto)
            .map(UserDto::toEntity)
            .flatMap(userRepository::save)
            .map(UserDto::fromEntity);
    }

    public Mono<UserDto> update(Long userId, UserDto userDto) {

        var updateUserDtoWithId = userDto
            .toBuilder()
            .id(userId)
            .build();

        return Mono.just(updateUserDtoWithId)
            .map(UserDto::toEntity)
            .flatMap(userRepository::update)
            .map(UserDto::fromEntity);
    }

}
