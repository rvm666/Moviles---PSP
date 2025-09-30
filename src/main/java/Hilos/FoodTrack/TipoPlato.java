package Hilos.FoodTrack;


import java.util.Random;

public enum TipoPlato {
    ENSALADA(2000, "🥗"),
    PASTA(3000, "🍝"),
    PIZZA(4000, "🍕"),
    CARNE(5000, "🥩");

    private final int tiempoMs;
    private final String emoji;

    public int getTiempoMs() {
        return tiempoMs;
    }

    public String getEmoji() {
        return emoji;
    }

    TipoPlato(int tiempoMs, String emoji) {
        this.tiempoMs = tiempoMs;
        this.emoji = emoji;
    }

    public TipoPlato aleatorio() {
        TipoPlato[] platos = values();
        int indiceAleatorio = new Random().nextInt(platos.length);
        return platos[indiceAleatorio];
    }

}
