package it.epicode.gestione_prenotazioni.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtenteConfig {

    @Value("${utente.defaultEmailDomain}")
    private String defaultEmailDomain;

    @Bean
    public String defaultEmailDomain() {
        return defaultEmailDomain;
    }
}