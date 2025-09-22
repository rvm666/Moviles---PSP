package Hilos.BancoVirtual;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class BancoService {
    Logger log = LoggerFactory.getLogger(BancoService.class);

    private IBancoVirtual saldo;
    private AtomicInteger operacionesNoRealizadas = new AtomicInteger(0);
    public BancoService(IBancoVirtual saldo) {
        this.saldo = saldo;
    }

    public int getOperacionesNoRealizadas() {
        return operacionesNoRealizadas.get();
    }

    public IBancoVirtual getSaldo() {
        return saldo;
    }

    public void setSaldo(IBancoVirtual saldo) {
        this.saldo = saldo;
    }

    public void work(){
        log.info("Saldo inicial: " + saldo.consultarSaldo());
        Random numeroRandom = new Random();
        final int NUM_HILOS = 50;
        List<Thread> hilos = new ArrayList<>();
        for (int i = 0; i < NUM_HILOS; i++) {
            Thread hilo = new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    double probabilidad = numeroRandom.nextDouble(100);
                    double cantidad = 0;
                    if (probabilidad < 60) {
                        cantidad = numeroRandom.nextDouble(1, 100);
                        boolean realizado = saldo.retirar(cantidad);
                        if (!realizado) {
                            operacionesNoRealizadas.incrementAndGet();
                        }
                    } else {
                        cantidad = numeroRandom.nextDouble(1,50);
                        saldo.ingresar(cantidad);
                    }
                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
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

        log.info("Saldo final: " + saldo.consultarSaldo());
    }
}
