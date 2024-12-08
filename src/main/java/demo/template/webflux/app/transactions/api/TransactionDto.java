package demo.template.webflux.app.transactions.api;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import demo.template.webflux.app.transactions.repository.TransactionStatus;
import demo.template.webflux.app.transactions.repository.TransactionType;
import lombok.Builder;

@Builder(toBuilder = true)
@JsonIgnoreProperties(value = {"id", "updatedAt", "createdAt"}, allowGetters = true)
public record TransactionDto(

    @Nullable
    Long id,

    @NotNull
    BigDecimal amount,

    @NotNull
    Long payeeId,

    @NotNull
    Long recipientId,

    TransactionType type,
    TransactionStatus status,
    Instant updatedAt,
    Instant createdAt

) {
}
