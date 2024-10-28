public class Ball {
    private int holderId; // ID del giocatore che detiene attualmente la palla
    private boolean gameEnded;
    private int winnerId;

    public Ball(int initialHolderId) {
        this.holderId = initialHolderId;
        this.gameEnded = false;
        this.winnerId = -1;
    }

    public synchronized boolean isGameEnded() {
        return gameEnded;
    }

    public synchronized int getWinnerId() {
        return winnerId;
    }

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
        System.out.println("Il Giocatore  " + playerId + " passa la palla.");

        int nextPlayerId = (playerId == 1) ? 2 : 1;

        // Simula la probabilità che il giocatore prenda la palla
        boolean catchSuccess = Math.random() < 0.9; // 90% di possibilità di prendere la palla

        if (!catchSuccess) {
            System.out.println("il giocatore " + nextPlayerId + " non ha preso la palla!");
            gameEnded = true;
            winnerId = playerId;
            notifyAll(); // Notifica gli altri thread
            return;
        } else {
            System.out.println("il giocatore " + nextPlayerId + " ha preso la palla.");
        }

        // Scambia il giocatore che ha la palla
        holderId = nextPlayerId;

        // Notifica gli altri thread
        notifyAll();
    }
}

