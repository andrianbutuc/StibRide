package andrian.stibride.model.db.dto;

/**
 * This class represent a line dto . The data carried is known by its key inherited from dto object .
 */
public class LineDto extends Dto<Integer>{
    /**
     * Creates a new instance of <code>Dto</code> with the key of the data.
     *
     * @param key key of the data.
     */
    public LineDto(Integer key) {
        super(key);
    }
}
