package andrian.stibride.model.searchpath;

import andrian.stibride.model.db.dto.FavoriteDto;
import andrian.stibride.model.db.dto.StationDto;
import andrian.stibride.model.db.exeception.RepositoryException;
import andrian.stibride.model.db.repository.FavoriteRepository;
import andrian.stibride.model.observer.Observable;

import java.util.List;

/**
 * This class represents the data model of application capable to search a path , add ,delete ,update the favorite path.
 */
public class Stib extends Observable implements StibInterface<StationDto,FavoriteDto>{
    private  PathSearch graph ;

    /**
     * Constructs the data model of this application .
     * @throws RepositoryException a repository exception .
     */
    public Stib() throws RepositoryException {
        this.graph = new PathSearch();
    }

    /**
     * Searches the shortest path between origin and destination .
     * @param origin a origin station dto.
     * @param destination a destination station dto.
     */
    public void searchPath(StationDto origin, StationDto destination) {
        try {
            List<StationView> path;
            graph = new PathSearch();
            path = graph.getShortestPathBetween(origin,destination);
            notifyObservers(path);
        } catch (RepositoryException e) {
            e.printStackTrace();
            notifyObservers("Error in searching path");
        }
    }

    /**
     * Add the favorite in the favorite table .
     * @param favorite a favorite dto .
     */
    public void addToFavorite(FavoriteDto favorite){
        try {
            FavoriteRepository repository = new FavoriteRepository();
            int i = repository.add(favorite);
            notifyObservers(repository.get(i));
        }catch (RepositoryException exception){
            exception.printStackTrace();
            notifyObservers("Error add favorite");
        }
    }

    /**
     * Deletes the favorite from the favorite table .
     * @param favorite a favorite dto .
     */
    public void deleteFromFavorite(FavoriteDto favorite){
        try {
            FavoriteRepository repository = new FavoriteRepository();
            repository.remove(favorite.getKey());
        }catch (RepositoryException exception){
            exception.printStackTrace();
            notifyObservers("Error delete favorite");
        }

    }
    /**
     * Updates the favorite in the favorite table .
     * @param favorite a favorite dto .
     */
    public void editFavorite(FavoriteDto favorite){
        try {
            FavoriteRepository repository = new FavoriteRepository();
            repository.add(favorite);
        }catch (RepositoryException exception){
            exception.printStackTrace();
            notifyObservers("Error delete favorite");
        }
    }

}
