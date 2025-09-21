package Hilos;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class HilosNormales {
    private static final int NUM_HILOS = 10;

    public static void main(String[] args) {

        AtomicLong sumaTotalVisitas = new AtomicLong(0);
        List<Thread> hilos = new ArrayList<>();

        for (int i = 0; i < NUM_HILOS; i++){
            Thread hilo = new Thread(() -> { sumaTotalVisitas.incrementAndGet(); });
            hilos.add(hilo);
            hilo.start();
        }

        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Total visitas: " + sumaTotalVisitas.get() );
    }

}
