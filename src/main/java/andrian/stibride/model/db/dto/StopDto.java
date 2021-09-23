package andrian.stibride.model.db.dto;

/**
 * This class represent a stop dto . The data carried is known by its key inherited from dto object .
 */
public class StopDto extends Dto<StopsKey>{
    private final int idOrder;

    /**
     * Constructs stop dto using a stopskey for key .
     * @param key a stopskey .
     * @param idOrder a integer .
     */
    public StopDto(StopsKey key, int idOrder) {
        super(key);
        this.idOrder = idOrder;
    }

    /**
     * A simple getter . Id order represents the stop order in the metro line .
     * @return a integer .
     */
    public int getIdOrder() {
        return idOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StopDto)) return false;
        if (!super.equals(o)) return false;

        StopDto stopDto = (StopDto) o;

        return getIdOrder() == stopDto.getIdOrder();
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getIdOrder();
        return result;
    }
}