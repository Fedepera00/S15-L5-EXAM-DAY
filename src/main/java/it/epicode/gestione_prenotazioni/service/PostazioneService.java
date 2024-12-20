package it.epicode.gestione_prenotazioni.service;

import it.epicode.gestione_prenotazioni.entity.Postazione;
import it.epicode.gestione_prenotazioni.enums.TipoPostazione;
import it.epicode.gestione_prenotazioni.exception.BadRequestException;
import it.epicode.gestione_prenotazioni.exception.ResourceNotFoundException;
import it.epicode.gestione_prenotazioni.repository.PostazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostazioneService {

    @Autowired
    private PostazioneRepository repository;

    // RECUPERO TUTTE LE POSTAZIONI
    public List<Postazione> getAllPostazioni() {
        return repository.findAll();
    }

    // RECUPERO LA POSTAZIONE PER ID
    public Postazione getPostazioneById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Postazione non trovata con ID: " + id));
    }

    // QUI CREO UNA NUOVA POSTAZIONE
    public Postazione createPostazione(Postazione postazione) {

        if (postazione.getCodiceUnivoco() == null || postazione.getCodiceUnivoco().isEmpty()) {
            throw new BadRequestException("Il codice univoco della postazione è obbligatorio.");
        }
        if (postazione.getDescrizione() == null || postazione.getDescrizione().isEmpty()) {
            throw new BadRequestException("La descrizione della postazione è obbligatoria.");
        }
        if (postazione.getEdificio() == null) {
            throw new BadRequestException("L'edificio associato alla postazione è obbligatorio.");
        }
        return repository.save(postazione);
    }

    // AGGIORNO POSTAZIONE ESISTENTE
    public Postazione updatePostazione(Long id, Postazione updatedPostazione) {
        Postazione postazione = getPostazioneById(id);

        if (updatedPostazione.getCodiceUnivoco() == null || updatedPostazione.getCodiceUnivoco().isEmpty()) {
            throw new BadRequestException("Il codice univoco aggiornato non può essere vuoto.");
        }
        postazione.setCodiceUnivoco(updatedPostazione.getCodiceUnivoco());
        postazione.setDescrizione(updatedPostazione.getDescrizione());
        postazione.setTipo(updatedPostazione.getTipo());
        postazione.setNumeroMassimoOccupanti(updatedPostazione.getNumeroMassimoOccupanti());
        postazione.setEdificio(updatedPostazione.getEdificio());
        return repository.save(postazione);
    }

    // CANCELLO
    public void deletePostazione(Long id) {
        Postazione postazione = getPostazioneById(id); // Verifica se esiste
        repository.delete(postazione);
    }

    // RICERCO PER TIPO E CITTA'
    public List<Postazione> findPostazioniByTipoAndCitta(TipoPostazione tipo, String citta) {
        if (tipo == null || citta == null || citta.isEmpty()) {
            throw new BadRequestException("Tipo e città sono campi obbligatori per la ricerca.");
        }
        return repository.findByTipoAndCitta(tipo, citta);
    }
}