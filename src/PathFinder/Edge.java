package PathFinder;

public class Edge {
    private Nodes neighbour ;
    private int weight ;

    public Edge(Nodes neighbour, int weight) {
        this.neighbour = neighbour;
        this.weight = weight;
    }

    public Nodes getNeighbour() {
        return neighbour;
    }

    public int getWeight() {
        return weight;
    }
}
