package supermercado;

import java.time.LocalTime;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Queue {

    boolean[] emptyCashier;
    int[] cashiers;
    boolean hasPaid;
    Random rnd=new Random();
    Semaphore sm;
    private int flag;
    Lock reLock = new ReentrantLock(true);


    public Queue(int nCashiers) {
        sm = new Semaphore(nCashiers, true);
        cashiers = new int[nCashiers];
        emptyCashier = new boolean [nCashiers];
        setCashiers(cashiers, emptyCashier);
    }

    private void setCashiers(int[] cashiers, boolean[] emptyCashier) {
        for (int i=0;i<cashiers.length;i++){
            //cashiers[i]=i;
            emptyCashier[i]=true;
        }
    }


    public void pay() {
        try {
            sm.acquire();
            System.out.println(LocalTime.now() + " -- The client " + Thread.currentThread().getId() + " is in the queue");
            flag = selectCashier();
            if(flag!=99){
                TimeUnit.SECONDS.sleep(rnd.nextInt(4));
                System.out.println(LocalTime.now() + " -- The client " + Thread.currentThread().getId() + " has paid!");
            }
            emptyCashier[flag]=true;
        } catch (InterruptedException e) {
            return;
        } finally {
            sm.release();
        }

    }

    private int selectCashier() {
        reLock.lock();
        System.out.println(LocalTime.now() + " -- The client " + Thread.currentThread().getId() + " is choosing a cashier");
        try {
            for (int i=0; i<cashiers.length;i++){
                if (emptyCashier[i]){
                    emptyCashier[i]=false;
                    System.out.println(LocalTime.now() + " -- The client " + Thread.currentThread().getId() + " is in the cashier number " + i);
                    return i;
                }
            }
        } finally {
            reLock.unlock();
        }
        return 99;
    }
}
