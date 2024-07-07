package Entitys;

import World.Coordinates;

abstract public class Entity {
    protected String icon ;
    protected String iconUi;
    protected Coordinates coordinates;

    protected EntityType type;

    public Entity(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public EntityType getType() {
        return type;
    }

    public String getIcon() {
        return icon;
    }

    public String getIconUi() {
        return iconUi;
    }
}

