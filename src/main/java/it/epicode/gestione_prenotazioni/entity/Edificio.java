package it.epicode.gestione_prenotazioni.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "edifici")
public class Edificio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String indirizzo;
    private String citta;

    // COSTRUTTORE PARAMETRIZZATO
    public Edificio(String nome, String indirizzo, String citta) {
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.citta = citta;
    }

    // VUOTO
    public Edificio() {
    }
}