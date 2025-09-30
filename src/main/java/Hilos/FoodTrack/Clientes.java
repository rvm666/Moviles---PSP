package Hilos.FoodTrack;


import lombok.Data;

@Data
public class Clientes {
    private final int id;
    private TipoPlato platoElegido;

    public Clientes(int id, TipoPlato platoElegido) {
        this.id = id;
        this.platoElegido = platoElegido;
    }


    public void HacerPedido(){

        Pedido pedido = new Pedido(this.id, );
    }
}
