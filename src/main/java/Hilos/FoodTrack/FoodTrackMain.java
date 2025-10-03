package Hilos.FoodTrack;

public class FoodTrackMain {
    public static void main(String[] args) {
        FoodTrackService foodTrack;

        {
            try {
                foodTrack = new FoodTrackService();
                foodTrack.generadorPedidos();
                foodTrack.recogerPedidos();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }


}
