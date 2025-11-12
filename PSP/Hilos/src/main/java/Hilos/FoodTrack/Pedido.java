package Hilos.FoodTrack;

import lombok.Data;

@Data
public class Pedido {
    private final int clienteId;
    private final long tiempoPreparacion;
    private final TipoPlato plato;

    public Pedido(int clienteId, TipoPlato plato) {
        this.clienteId = clienteId;
        this.tiempoPreparacion = System.currentTimeMillis();
        this.plato = plato;
    }

    @Override
    public String toString() {
        return "Pedido" + clienteId + "ha pedido" + plato;
    }
}
