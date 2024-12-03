package demo.template.webflux.helpers;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
@Slf4j
public class MethodAccessLoggerAspect {

    @Before("execution(* demo.template.webflux.domain..*(..))")
    public void logMethodEntry(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().toShortString();
        Object[] methodArgs = joinPoint.getArgs();
        log.info("Entering method: {}.{} with arguments: {}", className, methodName, methodArgs);
    }

}
