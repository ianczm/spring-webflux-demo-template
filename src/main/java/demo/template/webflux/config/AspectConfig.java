package demo.template.webflux.config;

/* https://docs.spring.io/spring-framework/reference/core/aop/ataspectj.html */

import demo.template.webflux.aspects.MethodAccessLoggerAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AspectConfig {

    @Bean
    public MethodAccessLoggerAspect methodAccessLoggerAspect() {
        return new MethodAccessLoggerAspect();
    }
}
