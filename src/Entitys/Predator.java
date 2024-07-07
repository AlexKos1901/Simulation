package Entitys;

import World.Coordinates;
import World.WorldMap;

public class Predator extends Creature {
    public static final String ICON = "\uD83D\uDC05";//"\uD83E\uDD81";
    public static final String ICONUI = "<font color=\"red\">\uD83D\uDC05</font>";
    private boolean eatYesterday = false;

    public Predator(Coordinates coordinates) {
        super(coordinates);
        super.icon = ICON;
        super.iconUi = ICONUI;

        super.type = EntityType.PREDATOR;
        super.targetType = EntityType.HERBIVORE;


        super.hp = 300;
        setMaxHp(hp);
        super.hp = 290;
        super.movePoints = 80;
        super.costOfMove = 40;

    }


    @Override
    public Coordinates moving(WorldMap map ) {

        if (hp == getMaxHp() && eatYesterday) {
            return coordinates; // отдыхает после еды , не теряя жизни
        }
        eatYesterday = false;
        return super.moving(map);
    }

    @Override
    public Integer eating(Integer freeMovePoints, MayBeEaten target) {
        eatYesterday  = true;
        return super.eating(freeMovePoints, target);

    }
}
