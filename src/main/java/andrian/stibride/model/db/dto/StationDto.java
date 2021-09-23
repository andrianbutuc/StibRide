package andrian.stibride.model.db.dto;

/**
 * This class represent a station dto . The data carried is known by its key inherited from dto object .
 */
public class StationDto extends Dto<Integer>{
    private final String name ;
    /**
     * Creates a new instance of <code>Dto</code> with the key of the data.
     *
     * @param key key of the data.
     */
    public StationDto(Integer key, String name) {
        super(key);
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("Name : null or empty");
        }
        this.name = name ;
    }

    /**
     * A simple getter .
     * @return a string .
     *
     */
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StationDto)) return false;
        if (!super.equals(o)) return false;

        StationDto that = (StationDto) o;

        return getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getName().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return name;
    }
}
