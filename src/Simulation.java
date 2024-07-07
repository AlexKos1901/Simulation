
import Renderer.MapRenderer;
import World.*;
public class Simulation {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("start");
        WorldMap worldMap = new WorldMap(30,15);

        WorldMover worldMover = new WorldMover(worldMap);
        MapRenderer renderer =new MapRenderer(worldMap);
        int numberOfTurns = 0;
        while (true) {
            System.out.println("ход номер "+numberOfTurns);
            renderer.renderMapInConcole();
            renderer.renderMapJSwing();
            worldMover.makeOneTurn();
            Thread.sleep(1000);
            numberOfTurns ++;
        }
   }
}
