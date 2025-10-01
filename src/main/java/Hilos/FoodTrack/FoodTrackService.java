package Hilos.FoodTrack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FoodTrackService {
    private BlockingQueue<Pedido> pedidosPendientes;
    private List<Thread> cocineros;

    public FoodTrackService() {
        this.pedidosPendientes = new ArrayBlockingQueue<>(10);
        this.cocineros = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Thread cocinero  = new Thread();
            cocineros.add(cocinero);
            cocinero.start();
        }
    }




}
