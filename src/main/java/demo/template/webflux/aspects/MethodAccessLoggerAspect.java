package demo.template.webflux.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Aspect
@Slf4j
public class MethodAccessLoggerAspect {

    @Before("execution(* demo.template.webflux.domain..*Controller.*(..))")
    public void logControllerInvocation(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] methodArgs = joinPoint.getArgs();
        log.info("Controller method: {} called with arguments: {}", methodName, methodArgs);
    }

    @AfterReturning(
        pointcut = "execution(* demo.template.webflux.domain..*Controller.*(..))",
        returning = "result"
    )
    public void logControllerReturn(JoinPoint joinPoint, Object result) {
        /* todo: investigate subscription calls */
        String methodName = joinPoint.getSignature().toShortString();
        switch (result) {
            case Mono<?> mono -> mono.share()
                .doOnNext(resultValue -> logReturnValue(methodName, resultValue))
                .subscribe();
            case Flux<?> flux -> flux.share()
                .collectList()
                .doOnNext(resultValue -> logReturnValue(methodName, resultValue))
                .subscribe();
            default -> logReturnValue(methodName, result);
        }
    }

    private static void logReturnValue(String methodName, Object resultValue) {
        log.info("Controller method: {} returned with result: {}", methodName, resultValue);
    }

}
