public class Player implements Runnable {
    private final int id;
    private final String name;
    private int passesMade;
    private final Ball ball;
    private final int totalPlayers;

    public Player(int id, String name, Ball ball, int totalPlayers) {
        this.id = id;
        this.name = name;
        this.passesMade = 0;
        this.ball = ball;
        this.totalPlayers = totalPlayers;
    }

    @Override
    public void run() {
        Thread currentThread = Thread.currentThread();
        System.out.println("Dati giocatore: ID: " + id + ", Nome: " + name +
                ", Priority: " + currentThread.getPriority());

        // Simula i passaggi finché il gioco non termina
        while (!ball.isGameEnded()) {
            ball.pass(id);
            passesMade++;
            System.out.println("Giocatore " + id + " (" + name + ", Thread ID: " + currentThread.getId()
                    + ") ha fatto " + passesMade + " passaggi.");

            // Simula il passaggio
            Thread.yield();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPassesMade() {
        return passesMade;
    }

}
