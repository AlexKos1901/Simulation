package World;

import Entitys.Creature;
import Entitys.Entity;
import Entitys.EntityType;
import Entitys.Grass;

import java.util.ArrayList;


import static Entitys.EntityType.HERBIVORE;
import static Entitys.EntityType.PREDATOR;

public class WorldMover {
    private final WorldMap mapForMoving ;

    public WorldMover(WorldMap mapForMoving) {
        this.mapForMoving = mapForMoving;
    }

    private void moveCreature(Creature creatureMoving){
        Coordinates newCoordinate = creatureMoving.moving(mapForMoving);
        if (newCoordinate!=null && newCoordinate !=creatureMoving.getCoordinates()){
            mapForMoving.changeEntityPosition(newCoordinate,creatureMoving);
        }
        if (creatureMoving.getHp()<=0) mapForMoving.deleteEntityFromBoard(creatureMoving.getCoordinates());
    }
    private void restorePopulation (){
        while (mapForMoving.getCurrentGrassCount()<mapForMoving.GRASS_MAX_COUNT){
            mapForMoving.createEntity(mapForMoving.getRandomFreePlace(), EntityType.GRASS);
        }
        while (mapForMoving.getCurrentHerbivoreCount()<mapForMoving.HERBIVORE_MAX_COUNT){
            mapForMoving.createEntity(mapForMoving.getRandomFreePlace(), HERBIVORE);
        }
        while (mapForMoving.getCurrentPredatorCount()<mapForMoving.PREDATOR_MAX_COUNT) {
            mapForMoving.createEntity(mapForMoving.getRandomFreePlace(), PREDATOR);
        }
    }
    private void deleteDeadEntities(){
        ArrayList<Coordinates> coordinatesEntities = new ArrayList<>(mapForMoving.getMap().keySet());
        for (Coordinates coordinates: coordinatesEntities) {
            Entity entity = mapForMoving.getMap().get(coordinates);
            switch (entity.getType()) {
                case HERBIVORE,PREDATOR ->{
                    Creature creature = (Creature) entity;
                    int hp = creature.getHp();
                    if (hp<=0 ) {
                        mapForMoving.deleteEntityFromBoard(creature.getCoordinates());
                    }
                }
                case GRASS ->{
                    Grass grass = (Grass) entity;
                    if (grass.getIntegrality()<=0 ) {
                        mapForMoving.deleteEntityFromBoard(grass.getCoordinates());
                    }
                }
            }
        }
    }

    public void makeOneTurn (){
        for (Coordinates coordinateOfCreatures: mapForMoving.getCoordinatesOfCreatures() ){
           Creature creatures = (Creature) mapForMoving.getEntityByCoordinate(coordinateOfCreatures);
           moveCreature(creatures);
        }
        deleteDeadEntities();
        restorePopulation();
    }
}
