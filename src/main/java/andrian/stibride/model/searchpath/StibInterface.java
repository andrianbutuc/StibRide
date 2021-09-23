package andrian.stibride.model.searchpath;

/**
 * A interface for the STIB RIDE data model .
 * @param <T> the station dto.
 * @param <K> the favorite path dto.
 */
public interface StibInterface<T, K> {

    /**
     * Searches the shortest path between origin and destination .
     * @param origin a origin station  .
     * @param destination a destination station .
     */
    void searchPath(T origin, T destination) ;

    /**
     * Add the favorite in the favorite table .
     * @param favorite a favorite path .
     */
    void addToFavorite(K favorite);
    /**
     * Deletes the favorite from the favorite table .
     * @param favorite a favorite path .
     */
    void deleteFromFavorite(K favorite);

    /**
     * Updates the favorite in the favorite table .
     * @param favorite a favorite path .
     */
    void editFavorite(K favorite);
}
