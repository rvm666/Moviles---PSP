package Hilos.Parking;

import lombok.Data;


public enum TipoVehiculo {
    NORMAL(1.0),
    VIP(2.0);

    private final double tarifaPorMinuto = 0;

    public double getTarifaPorMinuto() {
        return tarifaPorMinuto;
    }

    TipoVehiculo(double tarifaPorMinuto) {
        tarifaPorMinuto = tarifaPorMinuto;
    }
}
