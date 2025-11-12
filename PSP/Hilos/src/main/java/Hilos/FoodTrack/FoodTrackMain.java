package Hilos.FoodTrack;

public class FoodTrackMain {
    public static void main(String[] args) {
        FoodTrackService foodTrack;

        {
            try {
                foodTrack = new FoodTrackService();
                foodTrack.iniciar();
                foodTrack.terminar();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }


}
