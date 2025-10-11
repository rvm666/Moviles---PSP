package Hilos.Parking;

import lombok.Data;

import java.util.Random;


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

    public TipoVehiculo tipoAleatorio () {
        Random random = new Random();
        int aleatorio = random.nextInt(0, 100);
        if ( aleatorio < 80) {
            return NORMAL;
        } else {
            return VIP;
        }
    }
}
