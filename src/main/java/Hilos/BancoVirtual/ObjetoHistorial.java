package Hilos.BancoVirtual;

import lombok.Data;

@Data
public class ObjetoHistorial {
    double cantidadOperacion;
    String tipoOperacion;
    double saldoDespuesOperacion;
    String nombreHilo;

    public ObjetoHistorial(double cantidadOperacion, String tipoOperacion, double saldoDespuesOperacion, String nombreHilo) {
        this.cantidadOperacion = cantidadOperacion;
        this.tipoOperacion = tipoOperacion;
        this.saldoDespuesOperacion = saldoDespuesOperacion;
        this.nombreHilo = nombreHilo;
    }

}
