package org.example.utilities;

public class Usuario {
    private final String usuario;
    private int juegosGanados;
    private int juegosPerdidos;

    public Usuario(String usuario) {
        this.usuario = usuario;
        this.juegosGanados = 0;
        this.juegosPerdidos = 0;
    }


    public void aumentarGanados(){
        juegosGanados++;
    }

    public void aumentarPerdidos(){
        juegosPerdidos++;
    }


    public String toString(){
        return "Usuario: " + usuario + " | Juegos Ganados: " + juegosGanados + " | Juegos Perdidos: " + juegosPerdidos;
    }
}
