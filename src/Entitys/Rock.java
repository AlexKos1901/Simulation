package Entitys;

import World.Coordinates;

public class Rock extends Entity{
    public static final String ICON = "\uD83D\uDDFB";//"\uD83E\uDEA8";
    public static final String ICONUI = "<font color=\"black\">\uD83D\uDDFB</font>";

    public Rock(Coordinates coordinates) {
        super(coordinates);
        super.icon = ICON;
        super.iconUi = ICONUI;
        super.coordinates = coordinates;
        super.type = EntityType.ROCK;
    }
}
