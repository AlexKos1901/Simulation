package PathFinder;

import World.Coordinates;
import Entitys.EntityType;
import World.WorldMap;

import java.util.*;

public class PathFinder {
    private final Coordinates startPosition ;
    private ArrayList<Coordinates> coordinatesOfSearchingType;
    private final HashMap<Coordinates,Nodes > nodesList;
    private final WorldMap map;

    public PathFinder(WorldMap map, Coordinates startPosition, EntityType searchingType) {
        this.startPosition = startPosition;
        this.nodesList = new HashMap<>();
        coordinatesOfSearchingType = new ArrayList<>();
        this.map = map;
        ArrayList <Nodes> arrayOfNotCheckedNodes =  new ArrayList<>();
        arrayOfNotCheckedNodes.add(addNodeInGraph(startPosition));
        HashSet <Nodes> hashSetPassedNodes = new HashSet<>();
        while (!arrayOfNotCheckedNodes.isEmpty()){
            Nodes currentNode = arrayOfNotCheckedNodes.remove(0);
            ArrayList<Coordinates> arrayOfNotCheckedCoordinates =  map.getCoordinatesNeighborhoodsIfItsFreeOrTarget(currentNode.coordinates,searchingType);
            while(!arrayOfNotCheckedCoordinates.isEmpty()){
                Coordinates coordinateNeihbor =  arrayOfNotCheckedCoordinates.remove(0);
                Nodes adjacentNode = addNodeInGraph(coordinateNeihbor);
                int cost = Coordinates.calculateTransitionCost(currentNode.coordinates,coordinateNeihbor);
                Edge edge = new Edge(adjacentNode,cost);
                currentNode.edges.add(edge);
                if (!hashSetPassedNodes.contains(adjacentNode)){
                    if (!map.isCoordinateEmpty(coordinateNeihbor)&&(!coordinatesOfSearchingType.contains(coordinateNeihbor))){
                        coordinatesOfSearchingType.add(coordinateNeihbor);
                    } else if (!arrayOfNotCheckedNodes.contains(adjacentNode))
                    {arrayOfNotCheckedNodes.add(adjacentNode);}
                }
            }
            hashSetPassedNodes.add(currentNode);
        }
    }

    public ArrayList<Coordinates> getCoordinatesOfSearchingType() {
        return coordinatesOfSearchingType;
    }

    private Path findPath(Coordinates goal){

        HashMap<Nodes,Integer> queueNodes = new HashMap<>();
        //HashMap <Nodes,Integer> passedNodes = new HashMap<>() ;
        ArrayList<Nodes> passedNodeList = new ArrayList<>();
        Nodes goalNode = nodesList.get(goal);
        Nodes startNode = nodesList.get(startPosition);
        queueNodes.put(startNode,0);
        //int currentCost = 0;
        while (!queueNodes.isEmpty()){
            Nodes currentNode =  getNodeWithLowerCost(queueNodes);
           // if (currentCost!=0) currentCost = queueNodes.get(currentNode)-getHeuristicCost(currentNode,goalNode);
            queueNodes.remove(currentNode);
            if (currentNode.equals(goalNode)){
                //passedNodes.put(currentNode,currentCost);
                passedNodeList.add(currentNode);
                Collections.reverse(passedNodeList);
                return buildPath(passedNodeList,goalNode);
            }
            Iterator<Edge> edgeIterator = currentNode.edges.iterator();
            while (edgeIterator.hasNext()){
                Edge currentEdge = edgeIterator.next();
                int heuristicCost = getHeuristicCost(currentEdge.getNeighbour(),goalNode);
                int costToNeighborhoodNode  =/*currentCost+*/ currentEdge.getWeight() +heuristicCost;
                if (!passedNodeList.contains(currentEdge.getNeighbour()) ){
                    queueNodes.put(currentEdge.getNeighbour(), costToNeighborhoodNode);
                    if ((!queueNodes.containsKey(currentEdge.getNeighbour())
                        || (queueNodes.get(currentEdge.getNeighbour())) >= costToNeighborhoodNode))
                    {
                    queueNodes.remove(currentEdge.getNeighbour());
                    queueNodes.put(currentEdge.getNeighbour(),costToNeighborhoodNode);
                    }
                }
            }
            //passedNodes.put(currentNode,currentCost);
            passedNodeList.add(currentNode);
        }
        return null;
    }
    private Path buildPath( ArrayList<Nodes> passedNodesList,Nodes goalNode){
        Path goalPath = new Path( map.getEntityByCoordinate(goalNode.coordinates));
        Nodes startNode = nodesList.get(startPosition);
        Nodes buffNode = goalNode;
        goalPath.setTotalCost(0);
        for (Nodes currentNode : passedNodesList) {
            if (currentNode.equals(startNode)){
              Edge lastEdge =  startNode.findEdgeToTargetNode(buffNode);
              goalPath.setTotalCost(goalPath.getTotalCost() + lastEdge.getWeight());
              goalPath.putInCostAndCoordinate(goalPath.getTotalCost(),lastEdge.getNeighbour().coordinates);
              goalPath.putInCostAndCoordinate(2000,startPosition);
                return goalPath;
            }
            for (Edge edge : currentNode.edges) {
                if (edge.getNeighbour().equals(buffNode)){
                    goalPath.setTotalCost(goalPath.getTotalCost() + edge.getWeight());
                    goalPath.putInCostAndCoordinate(goalPath.getTotalCost(),edge.getNeighbour().coordinates);
                    break;
                }
            }
            buffNode = currentNode;
        }
        return goalPath;


    }
    private int getHeuristicCost (Nodes currentNode,Nodes goalNode){
        return ( 10 * (Math.abs(currentNode.coordinates.X-goalNode.coordinates.X)
                + Math.abs(currentNode.coordinates.Y-goalNode.coordinates.Y ) ) );
    }
    private Nodes getNodeWithLowerCost( HashMap<Nodes, Integer> queue){
        Iterator<Nodes> iteratorQueue = queue.keySet().iterator();
        Nodes nodeWithLowerCost = null;
        int costWayToNode = Integer.MAX_VALUE;
        while (iteratorQueue.hasNext()){
            Nodes currentNode = iteratorQueue.next();
            if (queue.get(currentNode) < costWayToNode){
                nodeWithLowerCost = currentNode;
                costWayToNode = queue.get(currentNode);
            }
        }
        return nodeWithLowerCost;

    }


    private Nodes addNodeInGraph(Coordinates coordinates){
        if (this.nodesList.containsKey(coordinates)) return nodesList.get(coordinates);

        Nodes node = new Nodes(coordinates,0);
        nodesList.put(coordinates,node);
        return node;
    }

    public Path getShortedPathToTarget(){
        ArrayList<Path> resultPath = new ArrayList<>();
        for (Coordinates target: coordinatesOfSearchingType){
            Path buffPath = findPath(target);
            if (buffPath!=null){
                resultPath.add(buffPath);
            }
        }
        Collections.sort(resultPath);
        if (!resultPath.isEmpty()) return resultPath.get(0);
        else return null;



    }
}
