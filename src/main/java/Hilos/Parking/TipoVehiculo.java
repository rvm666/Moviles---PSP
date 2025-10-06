package Hilos.Parking;

public enum TipoVehiculo {
    NORMAL(1.0),
    VIP(2.0);

    private final double tarifaPorMinuto = 0;

    TipoVehiculo(double tarifaPorMinuto) {
        tarifaPorMinuto = tarifaPorMinuto;
    }
}
