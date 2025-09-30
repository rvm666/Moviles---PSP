package Hilos.FoodTrack;

import lombok.Data;

@Data
public class Pedido {
    private final int clienteId;
    private final int tiempoPreparacion;
    private final TipoPlato plato;

    public Pedido(int clienteId, int tiempoPreparacion, TipoPlato plato) {
        this.clienteId = clienteId;
        this.tiempoPreparacion = tiempoPreparacion;
        this.plato = plato;
    }
}
