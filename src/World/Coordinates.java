package World;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Coordinates {
   final public int X;
   final public int Y;

   public Coordinates(int X, int Y) {
      this.X = X;
      this.Y = Y;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Coordinates that = (Coordinates) o;
      return X == that.X && Y == that.Y;
   }

   @Override
   public int hashCode() {
      return Objects.hash(X, Y);
   }

   public static int calculateTransitionCost (Coordinates from,Coordinates to){
      int difference = (from.X +from.Y)-(to.X+to.Y);
      int cost ;
      if (Math.abs(difference)==1) {
         cost = 10;
      }else cost =14;
      return cost;
   }

   @Override
   public String toString() {
      return "Coordinates{" +
              "X=" + X +
              ", Y=" + Y +
              '}';
   }
}
