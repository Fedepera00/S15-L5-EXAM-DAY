package it.epicode.gestione_prenotazioni.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EdificioConfig {

    @Value("${edificio.prefix}")
    private String edificioPrefix;

    @Bean
    public String edificioPrefix() {
        return edificioPrefix;
    }
}