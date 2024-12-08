package demo.template.webflux.app.transactions.repository;

import static jakarta.persistence.EnumType.STRING;

import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import java.math.BigDecimal;
import java.time.Instant;

import demo.template.webflux.app.users.repository.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
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
    private BigDecimal amount;

    @Column("recipient")
    private Long recipientId;

    @Transient
    private UserEntity recipient;

    @Column("payee")
    private Long payeeId;

    @Transient
    private UserEntity payee;

    @Column("type")
    @Enumerated(STRING)
    private TransactionType type;

    @Column("status")
    @Enumerated(STRING)
    private TransactionStatus status;

    @Column("updated_at")
    private Instant updatedAt;

    @Column("created_at")
    private Instant createdAt;
}
