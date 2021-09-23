package andrian.stibride.model.searchpath;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
/**
 * Ce code a été repris depuis le site https://www.baeldung.com/java-dijkstra et qu'il n'a pas besoin d'être tester
 * étant considéré comme correct . Cette classe a été légèrement modifier - l'attribut name a été remplacé par un
 * attribut id de type entier .
 */
public class Node {
    private final Integer id;

    private LinkedList<Node> shortestPath = new LinkedList<>();

    private Integer distance = Integer.MAX_VALUE;

    private final Map<Node, Integer> adjacentNodes = new HashMap<>();

    /**
     * Constructs the node with given id .
     * @param id an integer .
     */
    public Node(Integer id) {
        this.id = id;
    }

    /**
     * Sets the adjacent node with given distance .
     * @param destination a adjacent node .
     * @param distance an integer .
     */
    public void addDestination(Node destination, int distance) {
        adjacentNodes.put(destination, distance);
    }

    /**
     * A simple getter .
     * @return the id of node .
     */
    public Integer getId() {
        return id;
    }

    /**
     * A simple getter .
     * @return a map of adjacent nodes .
     */
    public Map<Node, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }

    /**
     * A simple getter .
     * @return an integer (the distance between 2 nodes).
     */
    public Integer getDistance() {
        return distance;
    }

    /**
     * A simple setter .
     * @param distance an integer .
     */
    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    /**
     * A simple getter .
     * @return a list of nodes .
     */
    public List<Node> getShortestPath() {
        return shortestPath;
    }

    /**
     * A simple setter .
     * @param shortestPath a linked list of nodes .
     */
    public void setShortestPath(LinkedList<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }

}
