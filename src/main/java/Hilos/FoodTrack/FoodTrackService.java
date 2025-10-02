package Hilos.FoodTrack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class FoodTrackService {
    private BlockingQueue<Pedido> pedidosPendientes = new ArrayBlockingQueue<>(10);
    private List<Thread> cocineros = new ArrayList<>();
    private List<Thread> clientes = new ArrayList<>();

    public FoodTrackService() {
        for (int i = 0; i < 3; i++) {
            Thread cocinero  = new Thread();
            cocineros.add(cocinero);
            cocinero.start();
        }
        for (int i = 0; i < 100; i++) {
            Thread cliente = new Thread();
            clientes.add(cliente);
            cliente.start();
        }
    }

    public TipoPlato platoAleatorio(){
        TipoPlato[] platos = TipoPlato.values();
        int indiceAleatorio = new Random().nextInt(platos.length);
        return platos[indiceAleatorio];
    }

    public void generadorPedidos(){
        clientes.forEach(cliente -> {
            Pedido p = new Pedido((int)cliente.getId(), platoAleatorio());
            try {
                pedidosPendientes.put(p);
                cliente.toString();
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void recogerPedidos(){
        cocineros.forEach(cocinero -> {
            try {
                pedidosPendientes.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
