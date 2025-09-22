package Hilos.BancoVirtual;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BancoMain {

    public static void main(String[] args) {
        Logger log = LoggerFactory.getLogger(BancoMain.class);
        log.info("=== BANCO VIRTUAL ===");
        log.info("50 clientes realizando 500 operaciones totales...");

        log.info("----USANDO REENTRANT LOCK----");
        IBancoVirtual bancoReentrantLock = new BancoReentrantLock();
        BancoService serviciosReentrantLock = new BancoService(bancoReentrantLock);
        serviciosReentrantLock.work();

        log.info("----USANDO SYNCHRONIZED----");
        IBancoVirtual bancoSincronized = new BancoSyncronized();
        BancoService serviciosSyncronized = new BancoService(bancoSincronized);
        serviciosSyncronized.work();
    }
}
