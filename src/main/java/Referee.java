public class Referee implements Runnable {
    private final Ball ball;

    public Referee(Ball ball) {
        this.ball = ball;
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
        int winnerId = ball.getWinnerId();
        System.out.println("L'arbitro dichiara il giocatore " + winnerId + " vincitore!");
    }
}
