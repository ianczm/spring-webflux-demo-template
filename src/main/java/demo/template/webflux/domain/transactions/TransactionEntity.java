package demo.template.webflux.domain.transactions;

import static jakarta.persistence.EnumType.STRING;

import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "transactions")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column("amount")
    @NotNull
    private BigDecimal amount;

    @Column("recipient")
    @NotNull
    private Long recipient;

    @Column("payee")
    @NotNull
    private Long payee;

    @Column("type")
    @Enumerated(STRING)
    private TransactionType type;

    @Column("status")
    @Enumerated(STRING)
    private TransactionStatus status;

    @Column("updated_at")
    @NotNull
    private Instant updatedAt;

    @Column("created_at")
    @NotNull
    private Instant createdAt;
}
