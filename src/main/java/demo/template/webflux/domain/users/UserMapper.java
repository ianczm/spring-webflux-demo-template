package demo.template.webflux.domain.users;

import static org.mapstruct.ReportingPolicy.IGNORE;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public abstract class UserMapper {

    public abstract UserEntity toEntity(UserDto userDto);

    public abstract UserDto toDto(UserEntity userEntity);

}
