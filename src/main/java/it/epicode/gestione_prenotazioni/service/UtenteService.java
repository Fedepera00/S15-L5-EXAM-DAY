package it.epicode.gestione_prenotazioni.service;

import it.epicode.gestione_prenotazioni.entity.Utente;
import it.epicode.gestione_prenotazioni.exception.BadRequestException;
import it.epicode.gestione_prenotazioni.exception.ResourceNotFoundException;
import it.epicode.gestione_prenotazioni.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository repository;

    public List<Utente> getAllUtenti() {
        return repository.findAll();
    }

    public Utente getUtenteById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utente non trovato con ID: " + id));
    }

    public Utente createUtente(Utente utente) {
        if (utente.getUsername() == null || utente.getUsername().isEmpty()) {
            throw new BadRequestException("Lo username non può essere nullo.");
        }
        return repository.save(utente);
    }

    public Utente updateUtente(Long id, Utente updatedUtente) {
        Utente utente = getUtenteById(id);
        if (updatedUtente.getUsername() == null || updatedUtente.getUsername().isEmpty()) {
            throw new BadRequestException("Lo username aggiornato non può essere vuoto.");
        }
        utente.setUsername(updatedUtente.getUsername());
        utente.setNomeCompleto(updatedUtente.getNomeCompleto());
        utente.setEmail(updatedUtente.getEmail());
        return repository.save(utente);
    }

    public void deleteUtente(Long id) {
        Utente utente = getUtenteById(id);
        repository.delete(utente);
    }
}