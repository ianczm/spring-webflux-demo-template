package demo.template.webflux.aspects;

import demo.template.webflux.exceptions.UnavailableRestException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.env.Environment;
import reactor.core.publisher.Mono;

@Aspect
@Slf4j
@RequiredArgsConstructor
public class ControllerFeatureFlagAspect {

    private final Environment environment;

    @Around("execution(public * *()) && @annotation(annotation)")
    public Mono<Object> checkFeatureFlag(ProceedingJoinPoint joinPoint, ControllerFeatureFlag annotation) {
        // Todo: should fix, executes twice for WebFlux
        return Mono.justOrEmpty(annotation)
            .map(ControllerFeatureFlag::value)
            .flatMap(flag -> handleFlag(joinPoint, flag))
            .then(Mono.defer(() -> proceed(joinPoint)));
    }

    private Mono<Void> handleFlag(ProceedingJoinPoint joinPoint, ControllerFeatureFlagType flag) {
        if (isFlagEnabled(flag)) {
            return Mono.empty();
        } else {
            log.warn("Endpoint called at {} is temporarily disabled for flag: {}", joinPoint.getSignature().toShortString(), flag);
            return Mono.error(new UnavailableRestException("Endpoint is temporarily disabled for flag: %s".formatted(flag)));
        }
    }

    private boolean isFlagEnabled(ControllerFeatureFlagType flag) {
        return environment.getProperty(flag.getPropertyKey(), Boolean.class, true);
    }

    @SneakyThrows
    private Mono<?> proceed(ProceedingJoinPoint pjp) {
        Object result = pjp.proceed();
        if (result instanceof Mono<?> mono) {
            return mono;
        } else {
            return Mono.just(result);
        }
    }
}
