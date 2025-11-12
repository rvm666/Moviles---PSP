package Hilos.BancoVirtual;


public class BancoSyncronized implements IBancoVirtual{
    private double saldo = 10000;

    @Override
    public synchronized boolean retirar(double cantidad) {
        boolean retirado = true;
        if (this.saldo - cantidad < 0) {
            retirado = false;
        } else {
            this.saldo -= cantidad;
        }
        return retirado;
    }

    @Override
    public synchronized void ingresar(double cantidad) {
        this.saldo += cantidad;
    }

    @Override
    public double consultarSaldo() {
        return this.saldo;
    }

}
