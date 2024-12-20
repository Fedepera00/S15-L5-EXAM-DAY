package it.epicode.gestione_prenotazioni.runner;

import com.github.javafaker.Faker;
import it.epicode.gestione_prenotazioni.entity.Prenotazione;
import it.epicode.gestione_prenotazioni.entity.Postazione;
import it.epicode.gestione_prenotazioni.entity.Utente;
import it.epicode.gestione_prenotazioni.service.PrenotazioneService;
import it.epicode.gestione_prenotazioni.service.PostazioneService;
import it.epicode.gestione_prenotazioni.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Order(4)
public class PrenotazioneRunner implements CommandLineRunner {

    @Autowired
    private PrenotazioneService prenotazioneService;
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private PostazioneService postazioneService;
    private final Faker faker = new Faker();

    @Override
    public void run(String... args) {
        System.out.println("\n⚙️ Creazione Prenotazioni ...");

        List<Utente> utenti = utenteService.getAllUtenti();
        List<Postazione> postazioni = postazioneService.getAllPostazioni();

        if (utenti.isEmpty() || postazioni.isEmpty()) {
            System.out.println("❌ Nessun utente o postazione trovata. Creazione Prenotazioni interrotta.");
            return;
        }

        Set<String> prenotazioniUniche = new HashSet<>();

        for (int i = 0; i < 50; i++) {
            Prenotazione prenotazione = new Prenotazione();

            LocalDate dataPrenotazione = LocalDate.now().plusDays(faker.number().numberBetween(1, 10));
            Utente utente = utenti.get(faker.random().nextInt(utenti.size()));
            Postazione postazione = postazioni.get(faker.random().nextInt(postazioni.size()));

            String uniqueKey = utente.getId() + "-" + postazione.getId() + "-" + dataPrenotazione;
            if (prenotazioniUniche.contains(uniqueKey)) {
                continue; // EVITO I DUPLICATI
            }

            prenotazioniUniche.add(uniqueKey);
            prenotazione.setUtente(utente);
            prenotazione.setPostazione(postazione);
            prenotazione.setDataPrenotazione(dataPrenotazione);

            try {
                prenotazioneService.createPrenotazione(prenotazione);
            } catch (Exception e) {
                System.out.println("⚠️ Errore nella creazione della prenotazione: " + e.getMessage());
            }
        }

        System.out.println("✅ Prenotazioni create con successo!");
    }
}