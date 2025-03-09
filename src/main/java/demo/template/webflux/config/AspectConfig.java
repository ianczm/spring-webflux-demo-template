package demo.template.webflux.config;

/* https://docs.spring.io/spring-framework/reference/core/aop/ataspectj.html */

import demo.template.webflux.aspects.ControllerFeatureFlagAspect;
import demo.template.webflux.aspects.MethodAccessLoggerAspect;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;

@Configuration
@EnableAspectJAutoProxy
@RequiredArgsConstructor
public class AspectConfig {

    private final Environment environment;

    @Bean
    public MethodAccessLoggerAspect methodAccessLoggerAspect() {
        return new MethodAccessLoggerAspect();
    }

    @Bean
    public ControllerFeatureFlagAspect controllerFeatureFlagAspect() {
        return new ControllerFeatureFlagAspect(environment);
    }
}
