package Hilos.ContadorCompartido;


import java.util.concurrent.atomic.AtomicInteger;


public class HilosAtomicos implements IContadorVisitas {
  private final AtomicInteger contadorVisitas = new AtomicInteger(0);


    @Override
    public void incrementarVisita() {
        contadorVisitas.incrementAndGet();
    }

    @Override
    public int getContador() {
        return contadorVisitas.get();
    }
}
