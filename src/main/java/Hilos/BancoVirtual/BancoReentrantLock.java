package Hilos.BancoVirtual;

import Hilos.ContadorCompartido.IContadorVisitas;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class BancoReentrantLock implements IBancoVirtual {
    ReentrantLock lock= new ReentrantLock();
    private double saldo = 10000;


    @Override
    public boolean retirar(double cantidad) {
        boolean retirado = true;
        lock.lock();
        try {
            if (this.saldo - cantidad < 0) {
                retirado = false;
            } else {
                this.saldo = saldo - cantidad;
            }
        } finally {
            lock.unlock();
        }
        return retirado;
    }

    @Override
    public void ingresar(double cantidad) {
        lock.lock();
        try {
            this.saldo = saldo + cantidad;
        } finally {
        lock.unlock();
        }
    }

    @Override
    public double consultarSaldo() {
        return this.saldo;
    }

    @Override
    public List<String> obtenerHistorial() {
        List<String> historial = new ArrayList<>();
        return List.of();
    }
}
