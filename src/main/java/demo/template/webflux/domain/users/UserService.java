package demo.template.webflux.domain.users;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

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

    public Flux<UserDto> findAll() {
        return userRepository.findAll()
            .map(UserDto::fromEntity);
    }

    public Mono<UserDto> findById(Long userId) {
        return userRepository.findById(userId)
            .switchIfEmpty(Mono.error(new NotFoundRestException("User not found", "User with id: %s does not exist".formatted(userId))))
            .map(UserDto::fromEntity);
    }

    public Mono<UserDto> create(UserDto createUserDto) {
        return Mono.just(createUserDto)
            .map(UserDto::toEntity)
            .flatMap(userRepository::save)
            .onErrorResume(DuplicateKeyException.class, e -> Mono.error(new RestException(BAD_REQUEST, "User creation failed", "The user with email %s already exists".formatted(createUserDto.email()))))
            .then(userRepository.findByEmail(createUserDto.email()))
            .doOnNext(createduserEntity -> log.info("User created successfully: {}.", createduserEntity))
            .map(UserDto::fromEntity);
    }

    public Mono<UserDto> update(Long userId, UserDto updateUserDto) {

        var validateUserExistsMono = userRepository.findById(userId)
            .switchIfEmpty(Mono.error(new NotFoundRestException("User update failed", "User with id: %s does not exist".formatted(userId))));

        return validateUserExistsMono
            .thenReturn(updateUserDto
                .toBuilder()
                .id(userId)
                .build())
            .map(UserDto::toEntity)
            .flatMap(userRepository::update)
            .doOnNext(updatedUserEntity -> log.info("User updated successfully: {}.", updatedUserEntity))
            .map(UserDto::fromEntity);
    }

}
