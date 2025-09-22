package Hilos.ContadorCompartido;

import java.util.ArrayList;
import java.util.List;



public class HilosServicio {

    private IContadorVisitas contador;

    public HilosServicio(IContadorVisitas contador) {
        this.contador = contador;
    }

    public IContadorVisitas getContador() {
        return contador;
    }

    public void setContador(IContadorVisitas contador) {
        this.contador = contador;
    }


    public void work() {
        final int NUM_HILOS = 1000;
        List<Thread> hilos = new ArrayList<>();

        for (int i = 0; i < NUM_HILOS; i++) {
            Thread hilo = new Thread(() -> {
                contador.incrementarVisita();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
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
        System.out.println("Total visitas: " + contador.getContador());
    }
}

