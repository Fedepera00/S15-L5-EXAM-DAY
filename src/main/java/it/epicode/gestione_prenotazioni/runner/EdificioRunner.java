package it.epicode.gestione_prenotazioni.runner;

import com.github.javafaker.Faker;
import it.epicode.gestione_prenotazioni.entity.Edificio;
import it.epicode.gestione_prenotazioni.service.EdificioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class EdificioRunner implements CommandLineRunner {

    @Autowired private EdificioService edificioService;
    private final Faker faker = new Faker();

    @Override
    public void run(String... args) {
        System.out.println("\n⚙️ Creazione Edifici...");

        // DATI BASE
        for (int i = 1; i <= 5; i++) {
            Edificio edificio = new Edificio("Edificio " + i, "Via Test " + i, "Città " + i);
            edificioService.createEdificio(edificio);
        }

        // DATI FAKE
        for (int i = 0; i < 50; i++) {
            Edificio edificio = new Edificio(
                    faker.company().name(),
                    faker.address().streetAddress(),
                    faker.address().city());
            edificioService.createEdificio(edificio);
        }
        System.out.println("✅ Edifici creati con successo!");
    }
}