package it.epicode.gestione_prenotazioni.repository;

import it.epicode.gestione_prenotazioni.entity.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {

    boolean existsByUtenteIdAndDataPrenotazione(Long utenteId, LocalDate dataPrenotazione);

    boolean existsByPostazioneIdAndDataPrenotazione(Long postazioneId, LocalDate dataPrenotazione);
}