package Hilos.Parking;

import lombok.Data;

@Data
public class Vehiculo implements Runnable {
    private int id;
    private TipoVehiculo tipoVehiculo;

    @Override
    public void run() {

    }
}
