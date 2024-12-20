package it.epicode.gestione_prenotazioni.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class PrenotazioneConfig {

    @Value("${prenotazione.prefix}")
    private String prenotazionePrefix;

    @Bean
    public String prenotazionePrefix() {
        return prenotazionePrefix;
    }

    @Bean
    public LocalDate defaultStartDate() {
        return LocalDate.now();
    }
}