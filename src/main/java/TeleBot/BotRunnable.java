package TeleBot;

public class BotRunnable implements Runnable{
    private volatile boolean running = true;
    private Bot bot;
    public BotRunnable(Bot bot){
        this.bot = bot;
    }
    @Override
    public void run() {
        bot.botConnect();
        while (running && !Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

