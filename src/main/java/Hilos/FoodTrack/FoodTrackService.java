package Hilos.FoodTrack;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;


public class FoodTrackService {
    Logger log = LoggerFactory.getLogger(FoodTrackService.class);
    private final BlockingQueue<Pedido> pedidosPendientes = new ArrayBlockingQueue<>(10);
    private final List<Thread> cocineros = new ArrayList<>();
    private final List<Thread> clientes = new ArrayList<>();

    public void iniciar() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            Thread cocinero = new Thread(() -> {
                try {
                    while (true) {
                        Pedido p = pedidosPendientes.take();
                        log.info("Se esta cocinando {} por cocinero {}", p.getPlato().getEmoji(), Thread.currentThread().getName());
                        Thread.sleep(p.getPlato().getTiempoMs());
                        log.info("Cocinero {} cocinó {}", Thread.currentThread().getName(), p.getPlato() + p.getPlato().getEmoji());
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            cocineros.add(cocinero);
            cocinero.start();
        }
        AtomicInteger id = new AtomicInteger(0);
        for (int i = 0; i < 100; i++) {
            Thread cliente = new Thread(() -> {
                int id2 = id.incrementAndGet();
                TipoPlato platoElegido = platoAleatorio();
                Pedido p = new Pedido(id2, platoElegido);
                try {
                    pedidosPendientes.put(p);
                    log.info("El cliente {} ha pedido {}", Thread.currentThread().getName(), platoElegido);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            clientes.add(cliente);
            cliente.start();
            Thread.sleep(500);
        }

    }

    public void terminar() throws InterruptedException {
        for(Thread c: clientes){c.join();}

        while (!pedidosPendientes.isEmpty()) {Thread.sleep(50);}

        for(Thread c: cocineros){c.interrupt();}

        for (Thread c: cocineros){c.join();}

        log.info("Se ha cerrado el foodtrack");
    }

    public TipoPlato platoAleatorio(){
        TipoPlato[] platos = TipoPlato.values();
        int indiceAleatorio = new Random().nextInt(platos.length);
        return platos[indiceAleatorio];
    }


}
