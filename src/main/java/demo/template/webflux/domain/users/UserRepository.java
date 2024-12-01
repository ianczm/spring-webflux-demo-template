package demo.template.webflux.domain.users;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends R2dbcRepository<UserEntity, Long> {

    @Query("""
        UPDATE
            users
        SET
            name = :#{#userEntity.name},
            email = :#{#userEntity.email},
            country = :#{#userEntity.country},
            updated_at = CURRENT_TIMESTAMP
        WHERE
            id = :#{#userEntity.id}
        RETURNING *
        """)
    Mono<UserEntity> update(UserEntity userEntity);

    Mono<UserEntity> findByEmail(String email);
}
