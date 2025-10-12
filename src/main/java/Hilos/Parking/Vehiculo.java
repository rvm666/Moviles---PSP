package Hilos.Parking;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Random;

@Data
public class Vehiculo implements Runnable {
    Logger log = LoggerFactory.getLogger(Vehiculo.class);
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
                log.info("Ha entrado {} con id {}", Thread.currentThread().getName(), id);
                Thread.sleep(tiempoRandom.nextInt(5000, 20000));

                Thread.sleep(1000);
                parking.salirParking(this);
                log.info("Ha salido {} con id {}", Thread.currentThread().getName(), id);
            } else {
                log.info("Vehiculo no hay plazas se va {} con id {}", Thread.currentThread().getName(), id);
                parking.noEntra();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
