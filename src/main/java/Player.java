public class Player implements Runnable {
    private final int id;
    private final String name;
    private int passesMade;
    private final Ball ball;

    public Player(int id, String name, Ball ball) {
        this.id = id;
        this.name = name;
        this.passesMade = 0;
        this.ball = ball;
    }

    @Override
    public void run() {
        Thread currentThread = Thread.currentThread();
        System.out.println("Dati giocatore: ID: " + id + ", Nome: " + name +
                ", Priority: " + currentThread.getPriority());

        // Simula i passaggi
        while (!ball.isGameEnded()) {
            ball.pass(id);
            passesMade++;
            System.out.println("giocatore " + id + " (Thread ID: " + currentThread.getId() +
                    ") passaggi fatti: " + passesMade);

            // Simula il passaggio
            Thread.yield();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }

        if (ball.getWinnerId() == id) {
            System.out.println("Il giocatore " + id + " vince la partita!");
        } else {
            System.out.println("Il giocatore " + id + " perde la partita.");
        }
    }
}
