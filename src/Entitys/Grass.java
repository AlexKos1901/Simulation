package Entitys;

import World.Coordinates;

public class Grass extends Entity implements MayBeEaten {
   public static final String ICON = "\uD83C\uDF31";
   public static final String ICONUI ="<font color=\"green\">\uD83C\uDF31</font>";

   private Integer integrality ;
   public Grass(Coordinates coordinates) {
      super(coordinates);
      super.icon = ICON;
      super.iconUi = ICONUI;
      super.type = EntityType.GRASS;
      this.integrality = 100;

   }
   @Override
   public Integer getDamaged(Integer pointsOfDamage){ // возвращаем количество принятых очков поражения
      int buffIntegrality = integrality;
      integrality  = integrality-pointsOfDamage;
      if (integrality>=0) return pointsOfDamage;
      else return buffIntegrality;

   }

   public Integer getIntegrality() {
      return integrality;
   }
}
