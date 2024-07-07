package Entitys;

import PathFinder.Path;
import PathFinder.PathFinder;
import World.Coordinates;
import World.WorldMap;

abstract public class Creature extends Entity {
    protected int hp;
    private int maxHp;
    protected int  movePoints;

    protected int costOfMove;

    protected EntityType targetType;

     public Creature(Coordinates coordinates) {
         super(coordinates);

     }

     public Coordinates moving(WorldMap map ) {
         decreaseHp();
         PathFinder pathFinder = new PathFinder(map,this.coordinates,targetType);
         if (!pathFinder.getCoordinatesOfSearchingType().isEmpty()) {
             Path path =  pathFinder.getShortedPathToTarget();

             int eatPoints = path.getRemaindMovePoints(movePoints);
             MayBeEaten target =  path.getTarget();
             int acceptedDamagePoints = target.getDamaged(eatPoints);
             regenerate(acceptedDamagePoints);

             return path.getAccessibleCoordinate(movePoints);

         } else return null;
    }
    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public void decreaseHp ( ){
        hp -= this.costOfMove;
    }


    public Integer eating(Integer freeMovePoints,MayBeEaten target){
        return target.getDamaged(freeMovePoints);
    }

    public  void regenerate ( Integer pointOfRegeneration){
        int result = hp +pointOfRegeneration;
        if (result>= getMaxHp()) {
            hp = maxHp;
        }else  hp = result;
    }

    public int getHp() {
        return hp;
    }
}
