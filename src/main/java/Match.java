import java.util.ArrayList;
import java.util.List;

public class Match {
    public static void main(String[] args) {
        int totalPlayers = 4;
        Ball ball = new Ball(1, totalPlayers); // Il giocatore 1 inizia con la palla

        // Creazione dei giocatori
        List<Player> players = new ArrayList<>();
        players.add(new Player(1, "Alice", ball, totalPlayers));
        players.add(new Player(2, "Marco", ball, totalPlayers));
        players.add(new Player(3, "Luca", ball, totalPlayers));
        players.add(new Player(4, "Giulia", ball, totalPlayers));

        Referee referee = new Referee(ball, players);

        List<Thread> playerThreads = new ArrayList<>();

        // Creazione dei thread per i giocatori
        for (Player player : players) {
            Thread thread = new Thread(player);
            if (player.getId() == 1)
                thread.setPriority(Thread.MAX_PRIORITY); // Alice ha priorità massima
            else
                thread.setPriority(Thread.NORM_PRIORITY);

            playerThreads.add(thread);
        }

        Thread refereeThread = new Thread(referee);

        // Avvia i thread dei giocatori
        for (Thread thread : playerThreads) {
            thread.start();
        }

        // Avvia il thread dell'arbitro
        refereeThread.start();

        // Attende che i thread dei giocatori terminino
        for (Thread thread : playerThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }

        // Attende che il thread dell'arbitro termini
        try {
            refereeThread.join();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("Match concluso e classifica salvata.");
    }
}
