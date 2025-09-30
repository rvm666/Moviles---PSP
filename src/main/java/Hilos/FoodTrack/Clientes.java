package Hilos.FoodTrack;

public class Clientes {
    private final int id;
    private TipoPlato platoElegido;

    public Clientes(int id, TipoPlato platoElegido) {
        this.id = id;
        this.platoElegido = platoElegido;
    }
}
