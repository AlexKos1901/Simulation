package PathFinder;



import World.Coordinates;
import Entitys.Entity;

import java.util.LinkedHashSet;
import java.util.Objects;

public class Nodes {
    private static int numberOfNodes = 0;
     int nodeNumber;
     int parentNodeNumber;
    Coordinates coordinates;
    LinkedHashSet<Edge> edges = new LinkedHashSet<>();
    Entity containEntity = null;
    public Nodes(Coordinates coordinates, int parentNodeNumber) {
        this.coordinates = coordinates;
        this.parentNodeNumber = parentNodeNumber;
    //    this.nodeNumber = numberOfNodes;
        numberOfNodes++;
    }

    public Edge findEdgeToTargetNode (Nodes targetNode){
        for (Edge edge : this.edges) {
            if (edge.getNeighbour().equals(targetNode)) {
                return edge;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nodes nodes = (Nodes) o;
        return Objects.equals(coordinates, nodes.coordinates); //&& Objects.equals(edges, nodes.edges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates, edges);
    }
}

