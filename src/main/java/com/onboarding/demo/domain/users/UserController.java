package com.onboarding.demo.domain.users;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping
    public Mono<ResponseEntity<List<UserDto>>> findAll() {
        return userRepository.findAll()
            .map(UserDto::fromEntity)
            .collectList()
            .map(ResponseEntity::ok);
    }

    @GetMapping("{userId}")
    public Mono<ResponseEntity<UserDto>> findById(@PathVariable("userId") Long userId) {
        return userRepository.findById(userId)
            .map(UserDto::fromEntity)
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<UserDto>> create(
        @RequestBody
        @Validated
        UserDto saveUserDto
    ) {
        return Mono.just(saveUserDto)
            .map(UserDto::toEntity)
            .flatMap(userRepository::save)
            .map(UserDto::fromEntity)
            .map(ResponseEntity::ok);
    }

    @PutMapping("{userId}")
    public Mono<ResponseEntity<UserDto>> update(

        @PathVariable("userId")
        Long userId,

        @RequestBody
        @Validated
        UserDto updateUserDto
    ) {

        var updateUserDtoWithId = updateUserDto
            .toBuilder()
            .id(userId)
            .build();

        return Mono.just(updateUserDtoWithId)
            .map(UserDto::toEntity)
            .flatMap(userRepository::update)
            .map(UserDto::fromEntity)
            .map(ResponseEntity::ok);
    }

}
