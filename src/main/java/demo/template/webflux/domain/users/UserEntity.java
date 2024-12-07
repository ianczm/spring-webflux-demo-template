package demo.template.webflux.domain.users;

import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "users")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue
    Long id;

    @Column("name")
    String name;

    @Email
    @NotBlank
    @Column("email")
    String email;

    @Column("country")
    String country;

    @Column("updated_at")
    Instant updated_at;

    @Column("created_at")
    Instant created_at;
}
