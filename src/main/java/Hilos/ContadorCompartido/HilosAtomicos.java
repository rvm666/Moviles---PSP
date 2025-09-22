package Hilos.ContadorCompartido;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class HilosAtomicos implements IContadorVisitas {
  private AtomicInteger contadorVisitas = new AtomicInteger(0);


    @Override
    public void incrementarVisita() {
        contadorVisitas.incrementAndGet();
    }

    @Override
    public int getContador() {
        return contadorVisitas.get();
    }
}
