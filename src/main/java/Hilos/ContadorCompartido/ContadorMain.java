package Hilos.ContadorCompartido;

public class ContadorMain {

    public static void main(String[] args) {

        System.out.println("Contador de visitas sin sincronizar:");
        IContadorVisitas contadorVisitasNormales = new HilosNormales();
        HilosServicio servicios = new HilosServicio(contadorVisitasNormales);
        servicios.work();

        System.out.println("\nContador de visitas sincronizado:");
        IContadorVisitas contadorVisitasSyncronized = new HilosSyncronize();
        HilosServicio serviciosSyncronized = new HilosServicio(contadorVisitasSyncronized);
        servicios.work();

        System.out.println("\nContador de visitas con Atomic:");
        IContadorVisitas contadorVisitasAtomic = new HilosAtomicos();
        HilosServicio serviciosAtomic = new HilosServicio(contadorVisitasAtomic);
        servicios.work();
    }
}
