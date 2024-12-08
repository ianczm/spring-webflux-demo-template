package demo.template.webflux.app.users.api;

import static org.springframework.http.HttpStatus.CREATED;

import java.util.List;

import demo.template.webflux.app.users.UserService;
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
            .map(ResponseEntity::ok);
    }

    @PostMapping
    public Mono<ResponseEntity<UserDto>> create(
        @RequestBody
        @Validated
        UserDto userDto
    ) {
        return userService.create(userDto)
            .map(createdUser -> ResponseEntity.status(CREATED).body(createdUser));
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
