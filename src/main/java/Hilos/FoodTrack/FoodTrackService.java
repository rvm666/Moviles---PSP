package Hilos.FoodTrack;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FoodTrackService {

    BlockingQueue<Pedido> pedidosPendientes = new ArrayBlockingQueue<>(10);

}
