package Entitys;

import World.Coordinates;

public class Tree extends Entity{
    public static final String ICON = "\uD83C\uDF32";
    public static final String ICONUI = "<font color=\"black\">\uD83C\uDF32</font>";

    public Tree(Coordinates coordinates) {
        super(coordinates);
        super.icon = ICON;
        super.iconUi = ICONUI;
        super.coordinates = coordinates;
        super.type = EntityType.TREE ;
    }
}
