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

    public Vehiculo(int id, Parking parking, TipoVehiculo tipoVehiculo){
        this.id = id;
        this.parking = parking;
        this.tipoVehiculo = tipoVehiculo;
    }


    @Override
    public void run() {
        try {
            Thread.sleep(2000);
            boolean entrado = parking.entrarParking(this);
            if(entrado){
                System.out.println("Ha entrado " + Thread.currentThread().getName() + " con id " + id);
                Thread.sleep(tiempoRandom.nextInt(5000, 20000));

                Thread.sleep(1000);
                parking.salirParking(this);
                System.out.println("Ha salido " + Thread.currentThread().getName() + " con id " + id);
            } else {
                System.out.println("Vehiculo no hay plazas se va " + Thread.currentThread().getName() + " con id " + id);
                parking.noEntra();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
