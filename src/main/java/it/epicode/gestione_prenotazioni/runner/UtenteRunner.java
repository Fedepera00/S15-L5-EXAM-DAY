package it.epicode.gestione_prenotazioni.runner;

import com.github.javafaker.Faker;
import it.epicode.gestione_prenotazioni.entity.Utente;
import it.epicode.gestione_prenotazioni.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class UtenteRunner implements CommandLineRunner {

    @Autowired private UtenteService utenteService;
    private final Faker faker = new Faker();

    @Override
    public void run(String... args) {
        System.out.println("\n⚙️ Creazione Utenti...");

        // UTENTI BASE
        for (int i = 1; i <= 5; i++) {
            Utente utente = new Utente("utente" + i, "Nome Utente " + i, "utente" + i + "@azienda.com");
            utenteService.createUtente(utente);
        }

        // UTENTI FAKE
        for (int i = 0; i < 50; i++) {
            Utente utente = new Utente(
                    faker.name().username(),
                    faker.name().fullName(),
                    faker.internet().emailAddress());
            utenteService.createUtente(utente);
        }

        System.out.println("✅ Utenti creati con successo!");
    }
}