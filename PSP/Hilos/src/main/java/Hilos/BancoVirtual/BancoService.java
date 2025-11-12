package Hilos.BancoVirtual;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class BancoService {
    Logger log = LoggerFactory.getLogger(BancoService.class);
    String tipoOperacion = "";
    private final List<ObjetoHistorial> historial;
    private final IBancoVirtual saldo;
    private final AtomicInteger operacionesNoRealizadas = new AtomicInteger(0);
    public BancoService(IBancoVirtual saldo, List<ObjetoHistorial> historial) {
        this.historial = new CopyOnWriteArrayList<>(historial);
        this.saldo = saldo;
    }


    public void work(){
        log.info("Saldo inicial: {}", saldo.consultarSaldo());
        long inicio = System.currentTimeMillis();
        Random numeroRandom = new Random();
        final int NUM_HILOS = 50;
        List<Thread> hilos = new ArrayList<>();
        for (int i = 0; i < NUM_HILOS; i++) {
            Thread hilo = new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    boolean realizado = true;
                    double probabilidad = numeroRandom.nextDouble(100);
                    double cantidad;
                    if (probabilidad < 60) {
                        cantidad = numeroRandom.nextDouble(1, 100);
                        realizado = saldo.retirar(cantidad);
                        tipoOperacion = "Retiro";
                        if (!realizado) {
                            operacionesNoRealizadas.incrementAndGet();
                        }
                    } else {
                        cantidad = numeroRandom.nextDouble(1,50);
                        saldo.ingresar(cantidad);
                        tipoOperacion = "Ingreso";
                    }
                    if (realizado) {
                        historial.add(new ObjetoHistorial(cantidad, tipoOperacion, saldo.consultarSaldo(), Thread.currentThread().getName()));
                    }

                    try {
                        Thread.sleep(numeroRandom.nextInt(150, 300));
                    } catch (InterruptedException e) {
                        log.info(e.getMessage());
                    }
                }
            });
            hilos.add(hilo);
            hilo.start();
        }
        long fin = System.currentTimeMillis();
        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                log.info(e.getMessage());
            }
        }

        log.info("Saldo final: {}", saldo.consultarSaldo());
        log.info("Tiempo de ejecución: {} ms", (fin - inicio));
        log.info("Operaciones no realizadas: {}", operacionesNoRealizadas);
        log.info("Operaciones en el historial: {}", historial.size());
        for(ObjetoHistorial obj : historial) {
            log.info(obj.toString());
        }
    }
}
