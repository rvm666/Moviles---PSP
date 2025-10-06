package Hilos.Parking;

import java.util.concurrent.Semaphore;

public class Parking {
    private Semaphore plazasNormales = new Semaphore(20, true);
    private Semaphore plazasVip = new Semaphore(5, true);

    public boolean entrarParking(Vehiculo vehiculo) {
        if(vehiculo.getTipoVehiculo() == TipoVehiculo.NORMAL){
            return plazasNormales.tryAcquire();
        }else{
            return plazasNormales.tryAcquire() || plazasVip.tryAcquire();
        }

    }

}
