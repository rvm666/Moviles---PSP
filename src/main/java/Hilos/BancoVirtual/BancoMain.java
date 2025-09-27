package Hilos.BancoVirtual;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BancoMain {

    public static void main(String[] args) {
        Logger log = LoggerFactory.getLogger(BancoMain.class);
        List<ObjetoHistorial> historial = List.of();
        log.info("=== BANCO VIRTUAL ===");
        log.info("50 clientes realizando 500 operaciones totales...");

        log.info("----USANDO REENTRANT LOCK----");
        IBancoVirtual bancoReentrantLock = new BancoReentrantLock();
        BancoService serviciosReentrantLock = new BancoService(bancoReentrantLock, historial);
        serviciosReentrantLock.work();
        log.info("Operaciones finalizadas: {}", 500 - serviciosReentrantLock.getOperacionesNoRealizadas());

        log.info("----USANDO SYNCHRONIZED----");
        IBancoVirtual bancoSincronized = new BancoSyncronized();
        BancoService serviciosSyncronized = new BancoService(bancoSincronized, historial);
        serviciosSyncronized.work();
        log.info("Operaciones finalizadas: {}", 500 - serviciosSyncronized.getOperacionesNoRealizadas());
    }
}
