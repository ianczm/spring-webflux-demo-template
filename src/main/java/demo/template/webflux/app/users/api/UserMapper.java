package demo.template.webflux.app.users.api;

import static org.mapstruct.ReportingPolicy.IGNORE;

import demo.template.webflux.app.users.repository.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public abstract class UserMapper {

    public abstract UserEntity toEntity(UserDto userDto);

    public abstract UserDto toDto(UserEntity userEntity);

}
