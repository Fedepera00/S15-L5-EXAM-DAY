package it.epicode.gestione_prenotazioni.service;

import it.epicode.gestione_prenotazioni.entity.Prenotazione;
import it.epicode.gestione_prenotazioni.exception.BadRequestException;
import it.epicode.gestione_prenotazioni.repository.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository repository;

    public List<Prenotazione> getAllPrenotazioni() {
        return repository.findAll();
    }

    public Prenotazione getPrenotazioneById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BadRequestException("Prenotazione non trovata con ID: " + id));
    }

    public Prenotazione createPrenotazione(Prenotazione prenotazione) {
        // CONTROLLO SE L'UTENTE HA GIA'PRENOTATO PER QUELLA DATA
        boolean utenteHaPrenotato = repository.existsByUtenteIdAndDataPrenotazione(
                prenotazione.getUtente().getId(), prenotazione.getDataPrenotazione());
        if (utenteHaPrenotato) {
            throw new BadRequestException("L'utente ha già una prenotazione per questa data.");
        }

        // CONTROLLO SE LA POSTAZIONE E' GIA' OCCUPATA PER QUELLA DTA
        boolean postazioneOccupata = repository.existsByPostazioneIdAndDataPrenotazione(
                prenotazione.getPostazione().getId(), prenotazione.getDataPrenotazione());
        if (postazioneOccupata) {
            throw new BadRequestException("La postazione non è disponibile per questa data.");
        }

        return repository.save(prenotazione);
    }

    public void deletePrenotazione(Long id) {
        Prenotazione prenotazione = getPrenotazioneById(id);
        repository.delete(prenotazione);
    }
}