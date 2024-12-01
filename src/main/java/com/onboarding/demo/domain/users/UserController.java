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

    private final UserService userService;

    @GetMapping
    public Mono<ResponseEntity<List<UserDto>>> findAll() {
        return userService.findAll()
            .collectList()
            .map(ResponseEntity::ok);
    }

    @GetMapping("{userId}")
    public Mono<ResponseEntity<UserDto>> findById(@PathVariable("userId") Long userId) {
        return userService.findById(userId)
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<UserDto>> create(
        @RequestBody
        @Validated
        UserDto userDto
    ) {
        return userService.create(userDto)
            .map(ResponseEntity::ok);
    }

    @PutMapping("{userId}")
    public Mono<ResponseEntity<UserDto>> update(

        @PathVariable("userId")
        Long userId,

        @RequestBody
        @Validated
        UserDto userDto
    ) {
        return userService.update(userId, userDto)
            .map(ResponseEntity::ok);
    }

}
