package io.w4t3rcs.task.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class AppConfig {
    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {

        };
    }
}
