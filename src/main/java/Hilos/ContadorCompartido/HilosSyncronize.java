package Hilos.ContadorCompartido;

import java.util.ArrayList;
import java.util.List;

public class HilosSyncronize implements IContadorVisitas{
   private int contadorVisitas;


    @Override
    public synchronized void incrementarVisita() {
        contadorVisitas++;
    }

    @Override
    public int getContador() {
        return contadorVisitas;
    }
}
