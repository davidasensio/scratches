public class Utiles {


    public static void postponeComputation(int delay, Runnable computation) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        computation.run();
    }
}

abstract class AquariumFish {
//    abstract String color;

    abstract String getColor();
}