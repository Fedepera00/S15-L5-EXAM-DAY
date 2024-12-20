package it.epicode.gestione_prenotazioni.entity;

import it.epicode.gestione_prenotazioni.enums.TipoPostazione;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "postazioni")
public class Postazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codiceUnivoco;
    private String descrizione;

    @Enumerated(EnumType.STRING)
    private TipoPostazione tipo;

    private int numeroMassimoOccupanti;

    @ManyToOne
    @JoinColumn(name = "edificio_id")
    private Edificio edificio;

    // COSTRUTTORE PARAMETRIZZATO
    public Postazione(String codiceUnivoco, String descrizione, TipoPostazione tipo, int numeroMassimoOccupanti, Edificio edificio) {
        this.codiceUnivoco = codiceUnivoco;
        this.descrizione = descrizione;
        this.tipo = tipo;
        this.numeroMassimoOccupanti = numeroMassimoOccupanti;
        this.edificio = edificio;
    }

    // VUOTO
    public Postazione() {
    }
}