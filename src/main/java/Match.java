public class Match {
    public static void main(String[] args) {
        Ball ball = new Ball(1); // Il giocatore 1 inizia con la palla

        Player player1 = new Player(1, "Alice", ball);
        Player player2 = new Player(2, "Marco", ball);
        Referee referee = new Referee(ball);


        Thread thread1 = new Thread(player1);
        Thread thread2 = new Thread(player2);
        Thread refereeThread = new Thread(referee);


        thread1.setPriority(Thread.MAX_PRIORITY);
        thread2.setPriority(Thread.NORM_PRIORITY);

        thread1.start();
        thread2.start();
        refereeThread.start();

        try {
            thread1.join();
            thread2.join();
            refereeThread.join();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("Match concluso.");

    }
}