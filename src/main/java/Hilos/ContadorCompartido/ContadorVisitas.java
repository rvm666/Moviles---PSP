package Hilos.ContadorCompartido;

public class ContadorVisitas {

    private int contadorVisitas;

    public void incrementarVisitas() {
        contadorVisitas++;
    }

    public int getContadorVisitas() {
        return contadorVisitas;
    }

    public synchronized void incrementarVisitasSyn() {
        contadorVisitas++;
    }
}
