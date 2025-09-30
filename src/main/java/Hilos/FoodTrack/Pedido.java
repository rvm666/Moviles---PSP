package Hilos.FoodTrack;

public class Pedido {
    private final int clienteId;
    private final int tiempoPreparacion;

    public Pedido(int clienteId, int tiempoPreparacion) {
        this.clienteId = clienteId;
        this.tiempoPreparacion = tiempoPreparacion;
    }
}
