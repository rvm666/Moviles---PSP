package Hilos.ContadorCompartido;

import java.util.ArrayList;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HilosServicio {
    Logger log = LoggerFactory.getLogger(HilosServicio.class);
    private final IContadorVisitas contador;

    public HilosServicio(IContadorVisitas contador) {
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
                    log.info(e.getMessage());
                }
            });
            hilos.add(hilo);
            hilo.start();
        }

        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                log.info(e.getMessage());
            }
        }
        log.info("Total visitas: {}", contador.getContador());
    }
}

