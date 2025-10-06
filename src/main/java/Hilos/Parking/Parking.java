package Hilos.Parking;

import java.util.concurrent.Semaphore;

public class Parking {
    private int normalesOcupadas = 0;
    private int vipOcupadas = 0;
    private double recaudado = 0.0;
    private Semaphore plazasNormales = new Semaphore(20, true);
    private Semaphore plazasVip = new Semaphore(5, true);

    public boolean entrarParking(Vehiculo vehiculo) {
        boolean entran = false;
        if(vehiculo.getTipoVehiculo() == TipoVehiculo.NORMAL){
            entran = plazasNormales.tryAcquire();
            if(entran){
                recaudado = recaudado + vehiculo.getTipoVehiculo().getTarifaPorMinuto();
                normalesOcupadas++;
            } else vipOcupadas++;
            return entran;
        }else{
            entran = plazasNormales.tryAcquire() || plazasVip.tryAcquire();
            if(entran){
                recaudado = recaudado + vehiculo.getTipoVehiculo().getTarifaPorMinuto();
                normalesOcupadas++;
            } else vipOcupadas++;
            return entran;
        }
    }


    public void salirParking(Vehiculo vehiculo) {
        if(!vehiculo.isPlazaEsVip()){
            plazasNormales.release();
            normalesOcupadas--;
        }else{
            plazasVip.release();
            vipOcupadas--;
        }
    }
}
