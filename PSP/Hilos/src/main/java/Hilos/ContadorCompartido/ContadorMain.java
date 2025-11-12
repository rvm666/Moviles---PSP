package Hilos.ContadorCompartido;

import Hilos.BancoVirtual.BancoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContadorMain {

    public static void main(String[] args) {
        Logger log = LoggerFactory.getLogger(BancoService.class);
        log.info("Contador de visitas sin sincronizar:");
        IContadorVisitas contadorVisitasNormales = new HilosNormales();
        HilosServicio servicios = new HilosServicio(contadorVisitasNormales);
        servicios.work();

        log.info("\nContador de visitas sincronizado:");
        IContadorVisitas contadorVisitasSyncronized = new HilosSyncronize();
        HilosServicio serviciosSyncronized = new HilosServicio(contadorVisitasSyncronized);
        serviciosSyncronized.work();

        log.info("\nContador de visitas con Atomic:");
        IContadorVisitas contadorVisitasAtomic = new HilosAtomicos();
        HilosServicio serviciosAtomic = new HilosServicio(contadorVisitasAtomic);
        serviciosAtomic.work();
    }
}
