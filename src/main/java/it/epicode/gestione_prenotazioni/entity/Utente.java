package it.epicode.gestione_prenotazioni.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "utenti")
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String nomeCompleto;
    private String email;

    // VUOTO
    public Utente() {
    }

    // COSTRUTTORE PARAMETRIZZATO
    public Utente(String username, String nomeCompleto, String email) {
        this.username = username;
        this.nomeCompleto = nomeCompleto;
        this.email = email;
    }
}