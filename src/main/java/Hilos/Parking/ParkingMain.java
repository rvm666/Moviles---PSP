package Hilos.Parking;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;


public class ParkingMain {

    public static void main(String[] args) {
        Logger log = LoggerFactory.getLogger(ParkingMain.class);
        Parking parking = new Parking();
        List<Thread> listaVehiculos = new ArrayList<>();

        long inicio = System.currentTimeMillis();

        Thread monitor = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()){
                parking.monitor();
                try {
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        monitor.start();

        for(int i = 0; i <= 200; i++){
            Vehiculo v = new Vehiculo(i, parking, TipoVehiculo.NORMAL.tipoAleatorio());
            Thread t = new Thread(v);
            listaVehiculos.add(t);
            t.start();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


        for(Thread v :listaVehiculos){
            try {
                v.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


        monitor.interrupt();

        long fin = System.currentTimeMillis();
        long tiempo = fin - inicio;

        log.info("---------DATOS FINALES--------");
        log.info("Coches que han entrado: {}", parking.getEntradasTotales());
        log.info("Coches que no han entrado: {}", parking.getVehiculosNoEntrados());
        log.info("Dinero recaudado: {}", parking.getRecaudado());
        log.info("Tiempo total: {} seg", tiempo/1000);
        log.info("-------------------------------");

    }
}
