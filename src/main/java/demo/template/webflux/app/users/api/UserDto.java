package demo.template.webflux.app.users.api;

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
    @NotBlank
    String email,

    @Size(min = 2, max = 2)
    String country

) {
}
