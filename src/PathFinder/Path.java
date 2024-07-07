package PathFinder;

import Entitys.MayBeEaten;
import World.Coordinates;
import Entitys.Entity;

import java.util.Objects;
import java.util.TreeMap;

public class Path implements Comparable {
    private TreeMap<Integer, Coordinates> costsAndCoordinates;
    private int totalCost;
    private Entity target;

    public Path( Entity target){
        this.target = target;
        this.costsAndCoordinates = new TreeMap<Integer, Coordinates>();
        this.totalCost = 0;
    }

    protected void putInCostAndCoordinate (int cost, Coordinates coordinates){
        costsAndCoordinates.put(cost,coordinates);
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public MayBeEaten getTarget() {
        return (MayBeEaten) target;
    }
    public Coordinates getLastCoordinateBeforeTarget(){
        Coordinates result = costsAndCoordinates.higherEntry(
                costsAndCoordinates.firstEntry().getKey()).getValue();
        if (result.equals(target.getCoordinates())) System.out.println("error");
        return result;
    }

    public Coordinates getAccessibleCoordinate(int movePoints){

        Coordinates result = costsAndCoordinates.ceilingEntry(totalCost - movePoints).getValue();
        if (result.equals(target.getCoordinates()) ) {
            return getLastCoordinateBeforeTarget();
        } else return result;

    }

    public int getRemaindMovePoints (int movePoints){
        int balanceMovePoints = movePoints- totalCost+costsAndCoordinates.firstKey();
        return Math.max(balanceMovePoints, 0);
    }


    @Override
    public int compareTo(Object o){
        if (o == null || getClass() != o.getClass()) return -1;
        Path path = (Path) o;
        return this.totalCost - path.totalCost;

    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Path path = (Path) o;
        return totalCost == path.totalCost && costsAndCoordinates.equals(path.costsAndCoordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(costsAndCoordinates, totalCost);
    }

    @Override
    public String toString() {
        return "Path{" +
                "totalCost=" + totalCost +
                '}';
    }
}

