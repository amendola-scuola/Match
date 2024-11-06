import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class Referee implements Runnable {
    private final Ball ball;
    private final List<Player> players;

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

        System.out.println("\nClassifica dei palleggi:");
        for (Player player : players) {
            System.out.println("Giocatore " + player.getName() + ": " + player.getPassesMade() + " passaggi");
        }

        // Salva la classifica in un file
        try (FileWriter writer = new FileWriter("classifica.txt")) {
            writer.write("Classifica dei palleggi:\n");
            for (Player player : players) {
                writer.write("Giocatore " + player.getName() + ": " + player.getPassesMade() + " passaggi\n");
            }
            System.out.println("Classifica salvata nel file 'classifica.txt'.");
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio della classifica: " + e.getMessage());
        }
    }
}
