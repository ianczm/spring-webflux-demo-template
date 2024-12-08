package demo.template.webflux.app.balances.api;

import java.math.BigDecimal;

public record UserBalanceDto(
    Long userId,
    BigDecimal incoming,
    BigDecimal outgoing
) {
}
