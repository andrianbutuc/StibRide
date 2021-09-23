package andrian.stibride.model.searchpath;


import java.util.HashSet;
import java.util.Set;
/**
 * Ce code a été repris depuis le site https://www.baeldung.com/java-dijkstra et qu'il n'a pas besoin d'être tester
 * étant considéré comme correct .
 */
public class Graph {

    private Set<Node> nodes = new HashSet<>();

    /**
     * Adds a note to the graph .
     * @param nodeA a node .
     */
    public void addNode(Node nodeA) {
        nodes.add(nodeA);
    }

    /**
     * A simple getter .
     * @return a set of nodes .
     */
    public Set<Node> getNodes() {
        return nodes;
    }

    /**
     * Simple setter
     * @param nodes a set of nodes .
     */
    public void setNodes(Set<Node> nodes) {
        this.nodes = nodes;
    }
}
