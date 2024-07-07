package World;


import java.util.*;
import Entitys.*;

public class WorldMap {
    private final HashMap<Coordinates, Entity> map = new HashMap<>();
    private final int SIZE_X;
    private final int SIZE_Y;
    protected final int PREDATOR_MAX_COUNT;
    protected final int HERBIVORE_MAX_COUNT;
    protected final int GRASS_MAX_COUNT;

    private int currentGrassCount = 0 ;
    private int currentHerbivoreCount = 0 ;
    private int currentPredatorCount = 0 ;



    public WorldMap(int SIZE_X, int SIZE_Y) {
        this.SIZE_X = SIZE_X;
        this.SIZE_Y = SIZE_Y;
        PREDATOR_MAX_COUNT = 4;
        HERBIVORE_MAX_COUNT = 12;
        GRASS_MAX_COUNT = 30;


         for (int i = 0; i < GRASS_MAX_COUNT; i++) {
            createEntity(getRandomFreePlace(), EntityType.GRASS);
        }
        for (int i = 0; i < 30; i++) {
            createEntity(getRandomFreePlace(), EntityType.TREE);
        }
        for (int i = 0; i <PREDATOR_MAX_COUNT; i++) {
            createEntity(getRandomFreePlace(), EntityType.PREDATOR);
        }
        for (int i = 0; i <HERBIVORE_MAX_COUNT; i++) {
            createEntity(getRandomFreePlace(), EntityType.HERBIVORE);
        }
        for (int i = 0; i < 30; i++) {
            createEntity(getRandomFreePlace(), EntityType.ROCK);
        }
    }

    public int getSIZE_X() {
        return SIZE_X;
    }

    public int getSIZE_Y() {
        return SIZE_Y;
    }

    protected Coordinates getRandomFreePlace() {
        while (true) {
            Coordinates coordinates = new Coordinates((int) (Math.random() * SIZE_X), (int) (Math.random() * SIZE_Y));
            if (!map.containsKey(coordinates)) {
                return coordinates;
            }
        }
    }

    protected void createEntity(Coordinates coordinates, EntityType entityType) {
        switch (entityType) {
            case GRASS -> {
                Grass grass = new Grass(coordinates);
                putEntityInCoordinates(coordinates, grass);
            }
            case ROCK -> {
                Rock rock = new Rock(coordinates);
                putEntityInCoordinates(coordinates, rock);
            }
            case HERBIVORE -> {
                Herbivore herbivore = new Herbivore(coordinates);
                putEntityInCoordinates(coordinates, herbivore);
            }
            case PREDATOR -> {
                Predator predator = new Predator(coordinates);
                putEntityInCoordinates(coordinates, predator);
            }
            case TREE -> {
                Tree tree = new Tree(coordinates);
                putEntityInCoordinates(coordinates, tree);
            }
        }
    }

    private void putEntityInCoordinates(Coordinates coordinates, Entity entity) {
        map.put(coordinates, entity);
        entity.setCoordinates(coordinates);
        switch (entity.getType()) {
            case GRASS -> currentGrassCount++;
            case HERBIVORE -> currentHerbivoreCount++;
            case PREDATOR -> currentPredatorCount++;
        }

    }

    protected void deleteEntityFromBoard(Coordinates coordinates) {
        switch (getEntityByCoordinate(coordinates).getType()) {
            case GRASS -> currentGrassCount--;
            case HERBIVORE-> currentHerbivoreCount--;
            case PREDATOR-> currentPredatorCount--;
        }
        map.remove(coordinates);
    }

    protected boolean isCoordinateInRange(Coordinates coordinates) {
        return (coordinates.X < SIZE_X && coordinates.X >= 0 && coordinates.Y < SIZE_Y && coordinates.Y >= 0);
    }

    public boolean isCoordinateEmpty(Coordinates coordinates) {
        return !map.containsKey(coordinates);
    }



    protected void changeEntityPosition(Coordinates newCoordinate, Entity entity) {
        deleteEntityFromBoard(entity.getCoordinates());
        putEntityInCoordinates(newCoordinate, entity);
    }


    public ArrayList<Coordinates> getCoordinatesNeighborhoodsOnBoard(Coordinates current) {
        ArrayList<Coordinates> coordinatesNeiborhuds = new ArrayList<>();
        Coordinates neib;
        for (int x = -1; x < 2; x++){
            for (int y = -1; y < 2; y++){
                if (x==0 && y == 0) continue;
                neib = new Coordinates(current.X + x, current.Y+y);
                if (isCoordinateInRange(neib)) coordinatesNeiborhuds.add(neib);
            }
        }
        if (coordinatesNeiborhuds.size() > 0) return coordinatesNeiborhuds;
        else return null;
    }
    public ArrayList<Coordinates> getCoordinatesNeighborhoodsIfItsFreeOrTarget(Coordinates coordinate, EntityType searchingType){
        ArrayList<Coordinates> coordinatesNeighbor = getCoordinatesNeighborhoodsOnBoard(coordinate);
        ArrayList<Coordinates> result = new ArrayList<>();
        for (Coordinates c:coordinatesNeighbor) {
            if(isCoordinateEmpty(c)) result.add(c);
            else if (map.get(c).getType()==searchingType) result.add(c);
        }
        return result;
    }
    public HashMap<Coordinates,Entity> getMap(){
        return map;
    }

    public Entity getEntityByCoordinate (Coordinates coordinate){
        return map.getOrDefault(coordinate, null);
    }
    protected ArrayList<Coordinates> getCoordinatesOfCreatures (){
        Iterator<Coordinates> itr = map.keySet().iterator();
        ArrayList<Coordinates> arrayOfCreatureCoordinates = new ArrayList<>();

        while (itr.hasNext()) {
            Coordinates coordinatesOfCreature = itr.next();
            Entity checkEntity = map.get(coordinatesOfCreature);

            if (checkEntity.getType() == EntityType.HERBIVORE || checkEntity.getType() ==EntityType.PREDATOR)
                arrayOfCreatureCoordinates.add(map.get(coordinatesOfCreature).getCoordinates());
        }
        return arrayOfCreatureCoordinates;
    }

    protected int getCurrentGrassCount() {
        return currentGrassCount;
    }

    protected int getCurrentHerbivoreCount() {
        return currentHerbivoreCount;
    }

    protected int getCurrentPredatorCount() {
        return currentPredatorCount;
    }
}

