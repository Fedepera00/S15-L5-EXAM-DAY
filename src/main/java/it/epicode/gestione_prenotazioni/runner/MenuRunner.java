package it.epicode.gestione_prenotazioni.runner;

import it.epicode.gestione_prenotazioni.entity.*;
import it.epicode.gestione_prenotazioni.enums.TipoPostazione;
import it.epicode.gestione_prenotazioni.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@Component
public class MenuRunner implements CommandLineRunner {

    @Autowired
    private EdificioService edificioService;

    @Autowired
    private PostazioneService postazioneService;

    @Autowired
    private PrenotazioneService prenotazioneService;

    @Autowired
    private UtenteService utenteService;

    private final Scanner scanner = new Scanner(System.in);
    private boolean exit = false;

    @Override
    public void run(String... args) {
        System.out.println("👋 Benvenuto nel sistema di gestione prenotazioni aziendali!");

        while (!exit) {
            mostraMenuPrincipale();
            int scelta = leggiSceltaUtente();

            switch (scelta) {
                case 1 -> menuEdifici();
                case 2 -> menuPostazioni();
                case 3 -> menuUtenti();
                case 4 -> menuPrenotazioni();
                case 5 -> {
                    System.out.println("👋 Grazie per aver utilizzato il sistema. Arrivederci!");
                    exit = true;
                }
                default -> System.out.println("❌ Scelta non valida. Riprova.");
            }
        }
    }

    private void mostraMenuPrincipale() {
        System.out.println("\n========================");
        System.out.println("=== MENU PRINCIPALE ===");
        System.out.println("========================");
        System.out.println("1. Gestisci Edifici");
        System.out.println("2. Gestisci Postazioni");
        System.out.println("3. Gestisci Utenti");
        System.out.println("4. Gestisci Prenotazioni");
        System.out.println("5. Esci");
        System.out.println("========================");
    }

    private int leggiSceltaUtente() {
        int scelta = -1;
        while (scelta == -1) {
            try {
                System.out.print("Scegli un'opzione: ");
                scelta = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Input non valido. Inserisci un numero.");
            }
        }
        return scelta;
    }

    private void menuEdifici() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== MENU EDIFICI ===");
            System.out.println("1. Visualizza tutti gli Edifici");
            System.out.println("2. Aggiungi un nuovo Edificio");
            System.out.println("3. Modifica un Edificio");
            System.out.println("4. Elimina un Edificio");
            System.out.println("5. Torna al Menu Principale");

            int scelta = leggiSceltaUtente();

            switch (scelta) {
                case 1 -> edificioService.getAllEdifici().forEach(System.out::println);
                case 2 -> {
                    Edificio edificio = new Edificio();
                    System.out.print("Nome: ");
                    edificio.setNome(scanner.nextLine());
                    System.out.print("Indirizzo: ");
                    edificio.setIndirizzo(scanner.nextLine());
                    System.out.print("Città: ");
                    edificio.setCitta(scanner.nextLine());
                    edificioService.createEdificio(edificio);
                    System.out.println("✅ Edificio aggiunto con successo!");
                }
                case 3 -> {
                    System.out.print("ID dell'Edificio da modificare: ");
                    Long id = Long.parseLong(scanner.nextLine());
                    Edificio edificio = edificioService.getEdificioById(id);
                    System.out.print("Nuovo Nome: ");
                    edificio.setNome(scanner.nextLine());
                    System.out.print("Nuovo Indirizzo: ");
                    edificio.setIndirizzo(scanner.nextLine());
                    System.out.print("Nuova Città: ");
                    edificio.setCitta(scanner.nextLine());
                    edificioService.updateEdificio(id, edificio);
                    System.out.println("✅ Edificio modificato con successo!");
                }
                case 4 -> {
                    System.out.print("ID dell'Edificio da eliminare: ");
                    Long id = Long.parseLong(scanner.nextLine());
                    edificioService.deleteEdificio(id);
                    System.out.println("✅ Edificio eliminato con successo!");
                }
                case 5 -> {
                    System.out.println("🔙 Tornando al menu principale...");
                    back = true;
                }
                default -> System.out.println("❌ Scelta non valida. Riprova.");
            }
        }
    }

    private void menuPostazioni() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== MENU POSTAZIONI ===");
            System.out.println("1. Visualizza tutte le Postazioni");
            System.out.println("2. Aggiungi una nuova Postazione");
            System.out.println("3. Modifica una Postazione");
            System.out.println("4. Elimina una Postazione");
            System.out.println("5. Ricerca Postazioni per Tipo e Città");
            System.out.println("6. Torna al Menu Principale");

            int scelta = leggiSceltaUtente();

            switch (scelta) {
                case 1 -> postazioneService.getAllPostazioni().forEach(System.out::println);
                case 2 -> aggiungiPostazione();
                case 3 -> modificaPostazione();
                case 4 -> eliminaPostazione();
                case 5 -> ricercaPostazioni();
                case 6 -> {
                    System.out.println("🔙 Tornando al menu principale...");
                    back = true;
                }
                default -> System.out.println("❌ Scelta non valida. Riprova.");
            }
        }
    }

    private void aggiungiPostazione() {
        Postazione postazione = new Postazione();
        System.out.print("Codice Univoco: ");
        postazione.setCodiceUnivoco(scanner.nextLine());
        System.out.print("Descrizione: ");
        postazione.setDescrizione(scanner.nextLine());
        System.out.print("Tipo PRIVATO, OPENSPACE, SALA_RIUNIONI: ");
        postazione.setTipo(TipoPostazione.valueOf(scanner.nextLine().toUpperCase()));
        System.out.print("Numero Massimo Occupanti: ");
        postazione.setNumeroMassimoOccupanti(Integer.parseInt(scanner.nextLine()));
        System.out.print("ID dell'Edificio: ");
        Long edificioId = Long.parseLong(scanner.nextLine());
        postazione.setEdificio(edificioService.getEdificioById(edificioId));
        postazioneService.createPostazione(postazione);
        System.out.println("✅ Postazione aggiunta con successo!");
    }

    private void modificaPostazione() {
        System.out.print("ID della Postazione da modificare: ");
        Long id = Long.parseLong(scanner.nextLine());
        Postazione postazione = postazioneService.getPostazioneById(id);
        System.out.print("Nuovo Codice Univoco: ");
        postazione.setCodiceUnivoco(scanner.nextLine());
        System.out.print("Nuova Descrizione: ");
        postazione.setDescrizione(scanner.nextLine());
        System.out.print("Nuovo Tipo PRIVATO, OPENSPACE, SALA_RIUNIONI: ");
        postazione.setTipo(TipoPostazione.valueOf(scanner.nextLine().toUpperCase()));
        System.out.print("Nuovo Numero Massimo Occupanti: ");
        postazione.setNumeroMassimoOccupanti(Integer.parseInt(scanner.nextLine()));
        postazioneService.updatePostazione(id, postazione);
        System.out.println("✅ Postazione modificata con successo!");
    }

    private void eliminaPostazione() {
        System.out.print("ID della Postazione da eliminare: ");
        Long id = Long.parseLong(scanner.nextLine());
        postazioneService.deletePostazione(id);
        System.out.println("✅ Postazione eliminata con successo!");
    }

    private void ricercaPostazioni() {
        System.out.println("\n=== RICERCA POSTAZIONI ===");
        System.out.print("Inserisci il tipo di postazione PRIVATO, OPENSPACE, SALA_RIUNIONI: ");
        TipoPostazione tipo = null;
        while (tipo == null) {
            try {
                tipo = TipoPostazione.valueOf(scanner.nextLine().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.print("❌ Tipo non valido. Riprova: ");
            }
        }

        System.out.print("Inserisci la città: ");
        String citta = scanner.nextLine();

        try {
            List<Postazione> postazioni = postazioneService.findPostazioniByTipoAndCitta(tipo, citta);
            if (postazioni.isEmpty()) {
                System.out.println("⚠️ Nessuna postazione trovata per i criteri specificati.");
            } else {
                System.out.println("✅ Postazioni trovate:");
                postazioni.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("❌ Errore durante la ricerca: " + e.getMessage());
        }
    }

    private void menuUtenti() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== MENU UTENTI ===");
            System.out.println("1. Visualizza tutti gli Utenti");
            System.out.println("2. Aggiungi un nuovo Utente");
            System.out.println("3. Modifica un Utente");
            System.out.println("4. Elimina un Utente");
            System.out.println("5. Torna al Menu Principale");

            int scelta = leggiSceltaUtente();

            switch (scelta) {
                case 1 -> utenteService.getAllUtenti().forEach(System.out::println);
                case 2 -> {
                    Utente utente = new Utente();
                    System.out.print("Username: ");
                    utente.setUsername(scanner.nextLine());
                    System.out.print("Nome Completo: ");
                    utente.setNomeCompleto(scanner.nextLine());
                    System.out.print("Email: ");
                    utente.setEmail(scanner.nextLine());
                    utenteService.createUtente(utente);
                    System.out.println("✅ Utente aggiunto con successo!");
                }
                case 3 -> {
                    System.out.print("ID dell'Utente da modificare: ");
                    Long id = Long.parseLong(scanner.nextLine());
                    Utente utente = utenteService.getUtenteById(id);
                    System.out.print("Nuovo Username: ");
                    utente.setUsername(scanner.nextLine());
                    System.out.print("Nuovo Nome Completo: ");
                    utente.setNomeCompleto(scanner.nextLine());
                    System.out.print("Nuova Email: ");
                    utente.setEmail(scanner.nextLine());
                    utenteService.updateUtente(id, utente);
                    System.out.println("✅ Utente modificato con successo!");
                }
                case 4 -> {
                    System.out.print("ID dell'Utente da eliminare: ");
                    Long id = Long.parseLong(scanner.nextLine());
                    utenteService.deleteUtente(id);
                    System.out.println("✅ Utente eliminato con successo!");
                }
                case 5 -> {
                    System.out.println("🔙 Tornando al menu principale...");
                    back = true;
                }
                default -> System.out.println("❌ Scelta non valida. Riprova.");
            }
        }
    }

    private void menuPrenotazioni() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== MENU PRENOTAZIONI ===");
            System.out.println("1. Visualizza tutte le Prenotazioni");
            System.out.println("2. Aggiungi una nuova Prenotazione");
            System.out.println("3. Elimina una Prenotazione");
            System.out.println("4. Torna al Menu Principale");

            int scelta = leggiSceltaUtente();

            switch (scelta) {
                case 1 -> prenotazioneService.getAllPrenotazioni().forEach(System.out::println);
                case 2 -> {
                    Prenotazione prenotazione = new Prenotazione();
                    System.out.print("ID Utente: ");
                    Long utenteId = Long.parseLong(scanner.nextLine());
                    prenotazione.setUtente(utenteService.getUtenteById(utenteId));
                    System.out.print("ID Postazione: ");
                    Long postazioneId = Long.parseLong(scanner.nextLine());
                    prenotazione.setPostazione(postazioneService.getPostazioneById(postazioneId));
                    System.out.print("Data Prenotazione (YYYY-MM-DD): ");
                    prenotazione.setDataPrenotazione(LocalDate.parse(scanner.nextLine()));
                    prenotazioneService.createPrenotazione(prenotazione);
                    System.out.println("✅ Prenotazione aggiunta con successo!");
                }
                case 3 -> {
                    System.out.print("ID della Prenotazione da eliminare: ");
                    Long id = Long.parseLong(scanner.nextLine());
                    prenotazioneService.deletePrenotazione(id);
                    System.out.println("✅ Prenotazione eliminata con successo!");
                }
                case 4 -> {
                    System.out.println("🔙 Tornando al menu principale...");
                    back = true;
                }
                default -> System.out.println("❌ Scelta non valida. Riprova.");
            }
        }
    }
}