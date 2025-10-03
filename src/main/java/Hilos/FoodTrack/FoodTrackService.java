package Hilos.FoodTrack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class FoodTrackService {
    private final BlockingQueue<Pedido> pedidosPendientes = new ArrayBlockingQueue<>(10);
    private final List<Thread> cocineros = new ArrayList<>();
    private final List<Thread> clientes = new ArrayList<>();

    public FoodTrackService() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            Thread cocinero  = new Thread((Runnable) new Cocineros(i), "Chef " + i);
            cocineros.add(cocinero);
            cocinero.start();
        }
        for (int i = 0; i < 100; i++) {
            Thread cliente = new Thread((Runnable) new Clientes(i, platoAleatorio()));
            clientes.add(cliente);
            cliente.start();
            Thread.sleep(500);
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
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void recogerPedidos(){
        cocineros.forEach(cocinero -> {
            try {
                Pedido p = pedidosPendientes.take();
                System.out.println("Se esta cocinando " + p.getPlato().getEmoji() + " por " + cocinero.getName());
                Thread.sleep(p.getPlato().getTiempoMs());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void fin(){

    }

}
