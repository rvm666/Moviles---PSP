package Hilos.BancoVirtual;


public interface IBancoVirtual {
    boolean retirar(double cantidad);
    void ingresar(double cantidad);
    double consultarSaldo();
}
