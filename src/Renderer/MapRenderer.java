package Renderer;

import World.Coordinates;
import World.WorldMap;

import javax.swing.*;

public class MapRenderer {
    WorldMap worldMap;
    JFrame frame;
    JLabel jl;
    private static final String BLANK_SPACE="  ";

    public MapRenderer(WorldMap worldMap) {
       this.worldMap = worldMap;

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Simulation");
        frame.setSize(worldMap.getSIZE_Y() * 30, worldMap.getSIZE_Y() * 20);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
        jl = new JLabel();
        frame.getContentPane().add(jl);


    }

    public void renderMapJSwing() throws InterruptedException {

        StringBuilder str = new StringBuilder("<html><pre>");
        for (int y = 0; y < worldMap.getSIZE_Y(); y++) {
            for (int x = 0; x < worldMap.getSIZE_X(); x++) {
                Coordinates coordinates = new Coordinates(x, y);
                if (worldMap.getMap().containsKey(coordinates)) str.append(worldMap.getMap().get(coordinates).getIconUi());
                else str.append(BLANK_SPACE);
            }
            str.append("<br>");
        }
        str.append("</pre></html>");
        jl.setText(str.toString());
        jl.setVisible(false);
        jl.updateUI();
        jl.setVisible(true);
        Thread.sleep(500);
    }



    public void renderMapInConcole() {
        for (int y = 0; y < worldMap.getSIZE_Y(); y++) {
            StringBuilder str = new StringBuilder("\u001B[43m");
            for (int x = 0; x < worldMap.getSIZE_X(); x++) {
                Coordinates coordinates = new Coordinates(x, y);
                if (worldMap.getMap().containsKey(coordinates)) {
                    str.append(worldMap.getMap().get(coordinates).getIcon());
                } else {
                    str.append("  ");

                }
            }
            System.out.println(str.toString() + "\u001B[40m");

        }
    }
}
