package Hilos.Parking;

import lombok.Data;

import java.util.Random;

@Data
public class Vehiculo implements Runnable {
    private int id;
    private boolean plazaEsVip;
    private Parking parking;
    private TipoVehiculo tipoVehiculo;
    Random tiempoRandom = new Random();
    @Override
    public void run() {
        try {
            Thread.sleep(2000);
            boolean entrado = parking.entrarParking(this);
            if(entrado){

                Thread.sleep(tiempoRandom.nextInt(10000, 25000));

                Thread.sleep(1000);
                parking.salirParking(this);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }



    }
}
