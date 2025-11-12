package Hilos.ContadorCompartido;


public class HilosNormales implements IContadorVisitas{
    private int contadorVisitas;


    @Override
    public void incrementarVisita() {
        contadorVisitas++;
    }

    @Override
    public int getContador() {
        return contadorVisitas;
    }
}
