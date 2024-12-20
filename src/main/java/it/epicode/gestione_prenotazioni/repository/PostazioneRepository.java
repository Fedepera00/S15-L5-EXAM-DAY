package it.epicode.gestione_prenotazioni.repository;

import it.epicode.gestione_prenotazioni.entity.Postazione;
import it.epicode.gestione_prenotazioni.enums.TipoPostazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostazioneRepository extends JpaRepository<Postazione, Long> {

    @Query("SELECT p FROM Postazione p WHERE p.tipo = :tipo AND p.edificio.citta = :citta")
    List<Postazione> findByTipoAndCitta(@Param("tipo") TipoPostazione tipo, @Param("citta") String citta);
}