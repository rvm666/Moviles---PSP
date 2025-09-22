package Hilos.ContadorCompartido;

import java.util.ArrayList;
import java.util.List;



public class HilosServicio {

    private static final int NUM_HILOS = 1000;
    ContadorVisitas contadorVisitas = new ContadorVisitas();
    List<Thread> hilos = new ArrayList<>();

    for (int i = 0; i < NUM_HILOS; i++){
        Thread hilo = new Thread(() -> {
            contadorVisitas.incrementarVisitas();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        hilos.add(hilo);
        hilo.start();
    }

    for (Thread hilo : hilos){
        try {
            hilo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
        System.out.println("Total visitas: " + contadorVisitas.getContadorVisitas() );
}
