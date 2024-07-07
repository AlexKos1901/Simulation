package Entitys;


import World.Coordinates;


public class Herbivore extends Creature implements MayBeEaten {
    public static final String ICON ="\uD83D\uDC02"; //"\uD83E\uDDAC";
    public static final String ICONUI ="<font color=\"blue\">\uD83D\uDC02</font>";

    public Herbivore(Coordinates coordinates) {
        super(coordinates);
        super.icon = ICON;
        super.iconUi = ICONUI;
        super.hp = 500;
        setMaxHp(hp);
        super.targetType = EntityType.GRASS;

        super.type = EntityType.HERBIVORE;
        super.movePoints = 50;
        super.costOfMove = 20;
    }



    @Override
    public Integer getDamaged(Integer pointsOfDamage) {
        int hpWas = hp;
        hp  = hp-pointsOfDamage;
        if (hp>=0) return pointsOfDamage;
        else return hpWas;

    }
}
