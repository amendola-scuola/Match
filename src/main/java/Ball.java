public class Ball {
    private int holderId; // ID del giocatore che detiene attualmente la palla
    private boolean gameEnded;
    private final int totalPlayers;

    public Ball(int initialHolderId, int totalPlayers) {
        this.holderId = initialHolderId;
        this.gameEnded = false;
        this.totalPlayers = totalPlayers;
    }

    // Verifica se il gioco è terminato
    public synchronized boolean isGameEnded() {
        return gameEnded;
    }

    // Metodo per passare la palla al prossimo giocatore
    public synchronized void pass(int playerId) {
        if (gameEnded) {
            return;
        }
        while (holderId != playerId && !gameEnded) {
            try {
                wait(); // Aspetta finché non è il turno del giocatore
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
        if (gameEnded) {
            return;
        }

        // Simula il passaggio della palla
        System.out.println("Il giocatore " + playerId + " passa la palla.");

        int nextPlayerId = getNextPlayerId(playerId);

        // Simula la probabilità che il prossimo giocatore prenda la palla
        boolean catchSuccess = Math.random() < 0.9; // 90% di possibilità di prendere la palla

        if (!catchSuccess) {
            System.out.println("Il giocatore " + nextPlayerId + " non ha preso la palla! La partita si conclude.");
            gameEnded = true;
            notifyAll(); // Notifica gli altri thread
            return;
        } else {
            System.out.println("Il giocatore " + nextPlayerId + " ha preso la palla.");
        }

        // Aggiorna il giocatore che ha la palla
        holderId = nextPlayerId;

        // Notifica gli altri thread
        notifyAll();
    }

    private int getNextPlayerId(int playerId) {

        int randomNumber = (int) (Math.random() * 4) + 1;

        return randomNumber != playerId ? randomNumber : getNextPlayerId(playerId);
    }
}
