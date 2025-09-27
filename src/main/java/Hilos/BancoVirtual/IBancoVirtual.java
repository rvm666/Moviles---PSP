package Hilos.BancoVirtual;

import java.util.List;

public interface IBancoVirtual {
    public boolean retirar(double cantidad);
    public void ingresar(double cantidad);
    public double consultarSaldo();
}
