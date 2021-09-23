package andrian.stibride.model.db.dto;

/**
 * This class represent a favorite dto .The data carried is known by its key inherited from dto object.
 */
public class FavoriteDto extends Dto<Integer> {
    private String name;
    private StationDto origin;
    private StationDto destination;

    /**
     * Constructs a favorite data transfer object .
     *
     * @param id          an integer .
     * @param name        a string .
     * @param origin      a station dto .
     * @param destination a station dto.
     */
    public FavoriteDto(int id, String name, StationDto origin, StationDto destination) {
        super(id);
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name : null or empty");
        }
        if (origin == null) {
            throw new IllegalArgumentException("origin : null ");
        }
        if (destination == null) {
            throw new IllegalArgumentException("destination : null ");
        }
        this.name = name;
        this.origin = origin;
        this.destination = destination;

    }

    /**
     * A simple getter .
     *
     * @return the string name .
     */
    public String getName() {
        return name;
    }

    /**
     * A simple getter .
     *
     * @return the origin station dto.
     */
    public StationDto getOrigin() {
        return origin;
    }

    /**
     * A simple getter .
     *
     * @return the destination station dto.
     */
    public StationDto getDestination() {
        return destination;
    }

    /**
     * A simple setter .
     * @param name a string .
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * A simple setter .
     * @param origin a station dto .
     */
    public void setOrigin(StationDto origin) {
        this.origin = origin;
    }

    /**
     * A simple setter .
     * @param destination a station dto
     */
    public void setDestination(StationDto destination) {
        this.destination = destination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FavoriteDto)) return false;
        if (!super.equals(o)) return false;

        FavoriteDto that = (FavoriteDto) o;

        if (!getName().equals(that.getName())) return false;
        if (!getOrigin().equals(that.getOrigin())) return false;
        return getDestination().equals(that.getDestination());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getOrigin().hashCode();
        result = 31 * result + getDestination().hashCode();
        return result;
    }
}
