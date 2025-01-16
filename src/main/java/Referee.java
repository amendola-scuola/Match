import java.io.*;
import java.util.Comparator;
import java.util.List;

public class Referee implements Runnable {
    private final Ball ball;
    private final List<Player> players;
    private final String fileName = "classifica.txt";

    public Referee(Ball ball, List<Player> players) {
        this.ball = ball;
        this.players = players;
    }

    @Override
    public void run() {
        synchronized (ball) {
            while (!ball.isGameEnded()) {
                try {
                    ball.wait();
                } catch (InterruptedException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
        System.out.println("L'arbitro dichiara che la partita è terminata.");

        // Stampa la classifica dei palleggi
        players.sort(new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                return Integer.compare(p2.getPassesMade(), p1.getPassesMade());
            }
        });

        // Legge e stampa l'ultima classifica
        readLastRanking();

        // Stampa la classifica dei palleggi corrente
        System.out.println("\nNuova Classifica dei passaggi:");
        for (Player player : players) {
            System.out.println("Giocatore " + player.getName() + ": " + player.getPassesMade() + " passaggi");
        }

        // Salva la classifica in un file
        saveCurrentRanking();

    }

    // Metodo per leggere e stampare a video l'ultima classifica salvata
    synchronized void readLastRanking() {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("Nessuna classifica precedente trovata.");
            return;
        }
        System.out.println("\nUltima classifica salvata:");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("Fine della classifica precedente.\n");
        } catch (IOException e) {
            System.err.println("Errore durante la lettura della classifica: " + e.getMessage());
        }
    }

    // Metodo per salvare la classifica corrente
    private synchronized void saveCurrentRanking() {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Classifica dei passaggi:\n");
            for (Player player : players) {
                writer.write("Giocatore " + player.getName() + ": " + player.getPassesMade() + " passaggi\n");
            }
            System.out.println("Classifica salvata nel file 'classifica.txt'.");
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio della classifica: " + e.getMessage());
        }
    }
}
