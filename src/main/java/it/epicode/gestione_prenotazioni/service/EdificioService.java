package it.epicode.gestione_prenotazioni.service;

import it.epicode.gestione_prenotazioni.entity.Edificio;
import it.epicode.gestione_prenotazioni.exception.BadRequestException;
import it.epicode.gestione_prenotazioni.exception.ResourceNotFoundException;
import it.epicode.gestione_prenotazioni.repository.EdificioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EdificioService {

    @Autowired
    private EdificioRepository repository;

    public List<Edificio> getAllEdifici() {
        return repository.findAll();
    }

    public Edificio getEdificioById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Edificio non trovato con ID: " + id));
    }

    public Edificio createEdificio(Edificio edificio) {
        if (edificio.getNome() == null || edificio.getNome().isEmpty()) {
            throw new BadRequestException("Il nome dell'edificio non può essere nullo.");
        }
        if (edificio.getCitta() == null || edificio.getCitta().isEmpty()) {
            throw new BadRequestException("La città dell'edificio non può essere nulla.");
        }
        return repository.save(edificio);
    }

    public Edificio updateEdificio(Long id, Edificio updatedEdificio) {
        Edificio edificio = getEdificioById(id);
        if (updatedEdificio.getNome() == null || updatedEdificio.getNome().isEmpty()) {
            throw new BadRequestException("Il nome aggiornato non può essere vuoto.");
        }
        edificio.setNome(updatedEdificio.getNome());
        edificio.setIndirizzo(updatedEdificio.getIndirizzo());
        edificio.setCitta(updatedEdificio.getCitta());
        return repository.save(edificio);
    }

    public void deleteEdificio(Long id) {
        Edificio edificio = getEdificioById(id);
        repository.delete(edificio);
    }
}