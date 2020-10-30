package supermercado;

public class Main {

    static final int nCajas = 4;

    public static void main(String[] args) {
        Thread clients[]=new Thread[50];
        Queue queue = new Queue(nCajas);

        for (int i = 0; i < clients.length ; i++) {
            clients[i]=new Thread(new Client(queue));
        }

        for (int i = 0; i < clients.length; i++) {
            clients[i].start();
        }
    }
}
