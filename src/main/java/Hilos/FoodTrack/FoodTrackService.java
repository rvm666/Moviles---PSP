package Hilos.FoodTrack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;


public class FoodTrackService {
    private final BlockingQueue<Pedido> pedidosPendientes = new ArrayBlockingQueue<>(10);
    private final List<Thread> cocineros = new ArrayList<>();
    private final List<Thread> clientes = new ArrayList<>();

    public void iniciar() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            Thread cocinero = new Thread(() -> {
                try {
                    while (true) {
                        Pedido p = pedidosPendientes.take();
                        System.out.println("Se esta cocinando " + p.getPlato().getEmoji() + " por cocinero" + Thread.currentThread().getName());
                        Thread.sleep(p.getPlato().getTiempoMs());
                        System.out.println("Cocinero " + Thread.currentThread().getName() + " cocinó " + p.getPlato() + p.getPlato().getEmoji());
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
                    System.out.println("El cliente " + Thread.currentThread().getName() + " ha pedido " + platoElegido);
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

        System.out.println("Se ha cerrado el foodtrack");
    }

    public TipoPlato platoAleatorio(){
        TipoPlato[] platos = TipoPlato.values();
        int indiceAleatorio = new Random().nextInt(platos.length);
        return platos[indiceAleatorio];
    }

    /*public void generadorPedidos(){
        AtomicInteger id = new AtomicInteger(0);
        clientes.forEach(cliente -> {
            Pedido p = new Pedido(id.getAndIncrement(), platoAleatorio());
            try {
                pedidosPendientes.put(p);
                cliente.toString();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }*/

    public void recogerPedidos(){

    }

    public void fin(){

    }

}
