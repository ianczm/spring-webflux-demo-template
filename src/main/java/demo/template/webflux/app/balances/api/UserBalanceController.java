package demo.template.webflux.app.balances.api;

import demo.template.webflux.app.balances.UserBalanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("users/{userId}/balances")
@RequiredArgsConstructor
public class UserBalanceController {

    private final UserBalanceService userBalanceService;

    @GetMapping
    Mono<ResponseEntity<UserBalanceDto>> findBalancesByUser(@PathVariable Long userId) {
        return userBalanceService.findBalancesByUser(userId)
            .map(ResponseEntity::ok);
    }
}
