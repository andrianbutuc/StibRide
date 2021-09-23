package andrian.stibride.model.db.dto;

/**
 * This class represent the key of stop dto .
 */
public class StopsKey {
    private final int idLine;
    private final int idStation;

    /**
     * Constructs a stops key .
     * @param idLine an integer .
     * @param idStation an integer .
     */
    public StopsKey(int idLine, int idStation) {
        this.idLine = idLine;
        this.idStation = idStation;
    }

    /**
     * A simple getter .
     * @return an integer .
     */
    public int getIdLine() {
        return idLine;
    }

    /**
     * A simple getter .
     * @return an integer .
     */
    public int getIdStation() {
        return idStation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StopsKey)) return false;

        StopsKey stopsKey = (StopsKey) o;

        if (getIdLine() != stopsKey.getIdLine()) return false;
        return getIdStation() == stopsKey.getIdStation();
    }

    @Override
    public int hashCode() {
        int result = getIdLine();
        result = 31 * result + getIdStation();
        return result;
    }
}
