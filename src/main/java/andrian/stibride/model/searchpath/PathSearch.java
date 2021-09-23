package andrian.stibride.model.searchpath;


import andrian.stibride.model.db.dto.StationDto;
import andrian.stibride.model.db.dto.StopDto;
import andrian.stibride.model.db.dto.StopsKey;
import andrian.stibride.model.db.exeception.RepositoryException;
import andrian.stibride.model.db.repository.StationRepository;
import andrian.stibride.model.db.repository.StopRepository;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represent a object capable to find the shortest path between two stations dto.
 */
public class PathSearch {
    private final Graph metro;

    /**
     * Constructs the graph and set the adjacent nodes .
     * @throws RepositoryException a repository exception .
     */
    public PathSearch() throws RepositoryException {
        this.metro = new Graph();
        constructGraph();
    }

    /**
     * Constructs the graph by setting the adjacent nodes .
     * @throws RepositoryException a repository exception .
     */
    private void constructGraph() throws RepositoryException {
        StopRepository stopRepository = new StopRepository();
        List<StopDto> allStops = stopRepository.getAll();
        HashMap<Integer, Node> allNodes = new HashMap<>();
        StopsKey idTemp;
        for (StopDto stop : allStops) {
            idTemp = stop.getKey();
            Node n = new Node(idTemp.getIdStation());
            allNodes.put(idTemp.getIdStation(),n);
        }
        for (StopDto stop : allStops) {
            idTemp = stop.getKey();
            var neighborsIds = stopRepository.getAllNeighbors(idTemp.getIdStation());
            Node current = allNodes.get(idTemp.getIdStation());
            for (Integer id : neighborsIds) {
                Node n = allNodes.get(id);
                current.addDestination(n, 1);
            }
            metro.addNode(current);
        }
        /*
         * Not the best way to implement the fact that both nodes are the same station -> to fix
         * Strange situation - in case of DELACROIX to MADOU 2 path are possible first pass by the station SIMONIS and
         * second pass by station GARE DU MIDI ,both have same distance in graph but in reality the first has 10
         * stations and second9 stations , this is due to the fact that between SIMONIS and ELISABETH the distance is
         * zero because in reality these 2 station are in the same building but on different level .
         */
        Node elisabeth = allNodes.get(8472);
        Node simonis = allNodes.get(8764);
        elisabeth.addDestination(simonis, 0);
        simonis.addDestination(elisabeth, 0);


    }

    /**
     * Calculates the shortest path between origin and destination and returns a list of station view from origin to
     * the destination . The list represent the shortest path from origin to destination .
     * @param origin a station dto.
     * @param destination a station dto .
     * @return a list of station view from origin to the destination .
     * @throws RepositoryException a repository exception .
     */
    public List<StationView> getShortestPathBetween(StationDto origin, StationDto destination) throws RepositoryException {
        if(origin == null || destination == null){
            throw new RepositoryException("Origin or destination null");
        }
        Integer idOrigin = origin.getKey();
        Node from = getNodeInGraph(idOrigin);

        Dijkstra.calculateShortestPathFromSource( from);

        List<StationView> path = new LinkedList<>();
        Node to = getNodeInGraph(destination.getKey());
        StopRepository stopRepository = new StopRepository();
        StationRepository stationRepository = new StationRepository();
        for (var node : to.getShortestPath()) {
            Integer id = node.getId();
            path.add(new StationView(stationRepository.get(id).getName(), stopRepository.getLinesOfStation(id)));
        }
        path.add(new StationView(destination.getName(), stopRepository.getLinesOfStation(destination.getKey())));
        return path ;
    }

    /**
     * Finds the node in the graph with given id and returns them otherwise returns null .
     * @param id a integer .
     * @return the node with given id ,otherwise returns null .
     */
    private Node getNodeInGraph(Integer id){
        Node n = null;
        for (Node node:metro.getNodes()){
            if(node.getId().equals(id)){
                n = node;
            }
        }
        return n;
    }
}
