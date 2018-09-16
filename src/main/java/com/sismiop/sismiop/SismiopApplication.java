package com.sismiop.sismiop;

import com.sismiop.sismiop.configuration.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing

public class SismiopApplication {

    public static void main(String[] args) {
        SpringApplication.run(SismiopApplication.class, args);
    }
}
