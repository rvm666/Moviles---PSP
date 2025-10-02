package Hilos.FoodTrack;

import lombok.Data;

@Data
public class Cocineros {
    private final int id;
    private int numeroPedidosRealizados;

    public Cocineros(int id) {
        this.id = id;
    }


}
