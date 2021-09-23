package andrian.stibride.presenter;

import andrian.stibride.model.db.dto.FavoriteDto;
import andrian.stibride.model.db.dto.StationDto;
import andrian.stibride.model.observer.Observable;
import andrian.stibride.model.observer.Observer;
import andrian.stibride.model.searchpath.StationView;
import andrian.stibride.model.searchpath.Stib;
import andrian.stibride.viewfx.ViewFxConstructor;

import java.util.List;

/**
 * This class is the presenter of application .
 */
public class StibPresenter implements Observer {
    private final Stib model;
    private final ViewFxConstructor view ;

    /**
     * Constructs a presenter for the application .
     * @param model a Stib data model .
     * @param view a ViewFxConstructor .
     */
    public StibPresenter(Stib model, ViewFxConstructor view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void update(Observable observable, Object arg) {
        if (arg instanceof List) {
            @SuppressWarnings("unchecked") List<StationView> path = (List<StationView>) arg;
            view.showPath(path);
        }
        if(arg instanceof FavoriteDto){
            FavoriteDto f = (FavoriteDto) arg ;
            view.addFavoriteInTable(f);
        }
        if(arg instanceof String){
            String error  = (String) arg;
            view.showErrorMessage(error);
        }
    }

    /**
     * Adds a favorite in the database .
     * @param favorite a favorite .
     */
    public void addFavorite(FavoriteDto favorite){
        if(favorite== null){
            throw new IllegalArgumentException("Favorite : null");
        }
        model.addToFavorite(favorite);
    }

    /**
     * Deletes a favorite from database .
     * @param favorite a favorite .
     */
    public void deleteFavorite(FavoriteDto favorite){
        if(favorite== null){
            throw new IllegalArgumentException("Favorite : null");
        }
        model.deleteFromFavorite(favorite);
    }

    /**
     * Updates a favorite in database .
     * @param favorite a favorite .
     */
    public void updateFavorite(FavoriteDto favorite){
        if(favorite== null){
            throw new IllegalArgumentException("Favorite : null");
        }
        model.editFavorite(favorite);
    }

    /**
     * Searches the shortest path from origin to destination .
     * @param origin a station dto .
     * @param destination a station dto .
     */
    public void search(StationDto origin, StationDto destination){
        if(origin == null || destination == null){
            throw new IllegalArgumentException("Origin or destination : null");
        }
        model.searchPath(origin,destination);

    }
}
