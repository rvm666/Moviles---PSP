package Hilos.ContadorCompartido;

public class ContadorVisitas implements IContadorVisitas {

    private int contadorVisitas;

    public void incrementarVisitas() {
        contadorVisitas++;
    }

    public int getContadorVisitas() {
        return contadorVisitas;
    }

    @Override
    public void incrementarVisita() {
        contadorVisitas++;
    }

    @Override
    public int getContador() {
        return contadorVisitas;
    }
}
