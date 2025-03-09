package demo.template.webflux.aspects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ControllerFeatureFlagType {

    BALANCES("app.feature-flags.controller.balances"),
    TRANSACTIONS("app.feature-flags.controller.transactions"),
    USERS("app.feature-flags.controller.users");

    private final String propertyKey;
}
