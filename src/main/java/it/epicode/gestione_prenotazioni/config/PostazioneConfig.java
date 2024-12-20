package it.epicode.gestione_prenotazioni.config;

import it.epicode.gestione_prenotazioni.enums.TipoPostazione;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostazioneConfig {

    @Value("${postazione.prefix}")
    private String postazionePrefix;

    @Value("${postazione.defaultTipoPostazione}")
    private String defaultTipoPostazione;

    @Bean
    public String postazionePrefix() {
        return postazionePrefix;
    }

    @Bean
    public TipoPostazione defaultTipoPostazione() {
        return TipoPostazione.valueOf(defaultTipoPostazione);
    }
}