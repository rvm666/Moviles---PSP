package Hilos.Parking;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParkingMain {

    public static void main(String[] args) {
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

        System.out.println("---------DATOS FINALES--------");
        System.out.println("Coches que han entrado: " + parking.getEntradasTotales());
        System.out.println("Coches que no han entrado: " + parking.getVehiculosNoEntrados());
        System.out.println("Dinero recaudado: " + parking.getRecaudado());
        System.out.println("Tiempo total: " + tiempo/1000 + "seg");
        System.out.println("-------------------------------");





    }
}
