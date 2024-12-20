package it.epicode.gestione_prenotazioni.repository;

import it.epicode.gestione_prenotazioni.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
}