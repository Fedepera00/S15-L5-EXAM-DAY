package it.epicode.gestione_prenotazioni.runner;

import com.github.javafaker.Faker;
import it.epicode.gestione_prenotazioni.entity.Edificio;
import it.epicode.gestione_prenotazioni.entity.Postazione;
import it.epicode.gestione_prenotazioni.enums.TipoPostazione;
import it.epicode.gestione_prenotazioni.service.EdificioService;
import it.epicode.gestione_prenotazioni.service.PostazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(2)
public class PostazioneRunner implements CommandLineRunner {

    @Autowired private PostazioneService postazioneService;
    @Autowired private EdificioService edificioService;
    private final Faker faker = new Faker();

    @Override
    public void run(String... args) {
        System.out.println("\n⚙️ Creazione Postazioni ...");

        List<Edificio> edifici = edificioService.getAllEdifici();
        if (edifici.isEmpty()) {
            System.out.println("❌ Nessun edificio trovato. Creazione Postazioni interrotta.");
            return;
        }

        // POSTAZIONI BASE
        for (int i = 1; i <= 5; i++) {
            Postazione postazione = new Postazione(
                    "POST-" + i,
                    "Stai leggendo la descrizione della  postazione " + i,
                    TipoPostazione.values()[i % TipoPostazione.values().length],
                    5 + i,
                    edifici.get(i % edifici.size()));
            postazioneService.createPostazione(postazione);
        }

        //POSTAZIONI FAKE
        for (int i = 0; i < 50; i++) {
            Postazione postazione = new Postazione(
                    faker.bothify("POST###"),
                    faker.lorem().sentence(),
                    TipoPostazione.values()[faker.random().nextInt(TipoPostazione.values().length)],
                    faker.number().numberBetween(1, 10),
                    edifici.get(faker.random().nextInt(edifici.size())));
            postazioneService.createPostazione(postazione);
        }

        System.out.println("✅ Postazioni create con successo!");
    }
}