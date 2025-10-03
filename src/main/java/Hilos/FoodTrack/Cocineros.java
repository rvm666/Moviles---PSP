package Hilos.FoodTrack;

import lombok.Data;

@Data
public class Cocineros implements Runnable {
    private final int id;
    private int numeroPedidosRealizados;

    public Cocineros(int id) {
        this.id = id;
    }


    @Override
    public void run() {

    }
}
