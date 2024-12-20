package it.epicode.gestione_prenotazioni.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @Bean
    public String appName() {
        return appName;
    }

    @Bean
    public String version() {
        return appVersion;
    }
}