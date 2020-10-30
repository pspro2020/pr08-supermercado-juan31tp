package supermercado;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Client implements Runnable {

    private Queue queue;
    Random rnd=new Random();

    public Client(Queue queue){
        this.queue=queue;
    }

    @Override
    public void run() {
        queue.pay();
    }
}
