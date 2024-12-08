package demo.template.webflux.app.users;

import static java.lang.Boolean.TRUE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import demo.template.webflux.app.users.api.UserDto;
import demo.template.webflux.app.users.api.UserMapper;
import demo.template.webflux.app.users.repository.UserRepository;
import demo.template.webflux.exceptions.NotFoundRestException;
import demo.template.webflux.exceptions.RestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Flux<UserDto> findAll() {
        return userRepository.findAll()
            .map(userMapper::toDto);
    }

    public Mono<UserDto> findById(Long userId) {
        return userRepository.findById(userId)
            .switchIfEmpty(Mono.error(new NotFoundRestException("User not found", "User with id: %s does not exist".formatted(userId))))
            .map(userMapper::toDto);
    }

    public Mono<UserDto> create(UserDto createUserDto) {
        return Mono.just(createUserDto)
            .map(userMapper::toEntity)
            .flatMap(userRepository::save)
            .onErrorResume(DuplicateKeyException.class, e -> Mono.error(new RestException(BAD_REQUEST, "User creation failed", "The user with email %s already exists".formatted(createUserDto.email()))))
            .then(userRepository.findByEmail(createUserDto.email()))
            .doOnNext(createduserEntity -> log.info("User created successfully: {}.", createduserEntity))
            .map(userMapper::toDto);
    }

    public Mono<UserDto> update(Long userId, UserDto updateUserDto) {

        return verifyUserExists(userId, "User update failed")
            .thenReturn(updateUserDto
                .toBuilder()
                .id(userId)
                .build())
            .map(userMapper::toEntity)
            .flatMap(userRepository::update)
            .doOnNext(updatedUserEntity -> log.info("User updated successfully: {}.", updatedUserEntity))
            .map(userMapper::toDto);
    }

    public Mono<Void> verifyUserExists(Long userId, String errorMessage) {
        return userRepository.existsById(userId)
            .filter(TRUE::equals)
            .switchIfEmpty(Mono.error(new NotFoundRestException(errorMessage, "User with id: %s does not exist".formatted(userId))))
            .then();
    }

}
