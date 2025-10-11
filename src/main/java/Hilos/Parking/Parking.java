package Hilos.Parking;

import lombok.Data;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class Parking {
    private AtomicInteger normalesOcupadas = new AtomicInteger(0);
    private AtomicInteger vipOcupadas = new AtomicInteger(0);
    private AtomicInteger entradasTotales = new AtomicInteger(0);
    private AtomicInteger vehiculosNoEntrados = new AtomicInteger(0);
    private double recaudado = 0.0;
    private Semaphore plazasNormales = new Semaphore(20, true);
    private Semaphore plazasVip = new Semaphore(5, true);

    public boolean entrarParking(Vehiculo vehiculo) {
        boolean entran = false;
        if(vehiculo.getTipoVehiculo() == TipoVehiculo.NORMAL){
            entran = plazasNormales.tryAcquire();
            if(entran){
                normalesOcupadas.incrementAndGet();
                entradasTotales.incrementAndGet();
            }
            return entran;
        }else{
            if(plazasNormales.tryAcquire()){
                vehiculo.setPlazaEsVip(false);
                normalesOcupadas.incrementAndGet();
                entradasTotales.incrementAndGet();
                entran = true;
            } else if (plazasVip.tryAcquire()) {
                vehiculo.setPlazaEsVip(true);
                vipOcupadas.incrementAndGet();
                entradasTotales.incrementAndGet();
                entran = true;
            }
            return entran;
        }
    }


    public void salirParking(Vehiculo vehiculo) {
        if(vehiculo.isPlazaEsVip()){
            plazasVip.release();
            synchronized (this){
                recaudado += vehiculo.getTipoVehiculo().getTarifaPorMinuto();
            }
            vipOcupadas.decrementAndGet();
        }else{
            plazasNormales.release();
            synchronized (this){
                recaudado += vehiculo.getTipoVehiculo().getTarifaPorMinuto();
            }
            normalesOcupadas.decrementAndGet();
        }
    }

    public void noEntra(){
        vehiculosNoEntrados.incrementAndGet();
    }

    public void monitor(){
        System.out.println("-------SEGUIMIENTO PARKING------");
        System.out.println("Plazas normales ocupadas: " + normalesOcupadas + "/20");
        System.out.println("Plazas VIP ocupadas: " + vipOcupadas + "/5");
        System.out.println("Recaudado: " + recaudado);
        System.out.println("--------------------------------");
    }

    public void estadisticasFinales(){
        System.out.println("Coches que han entrado: " + entradasTotales);
        System.out.println("Coches que no han entrado: " + vehiculosNoEntrados);
        System.out.println("Dinero recaudado: " + recaudado);
    }
}
