package andrian.stibride.model.searchpath;

import java.util.List;

/**
 * This class is the representation of a station with his name and the list of lines that belongs to that station dto .
 */
public class StationView {
    private final String name ;
    private final List<Integer> lines;

    /**
     * Constructs a view of station .
     * @param name a string name of station .
     * @param lines the list of lines that belongs to that station name .
     */
    public StationView(String name, List<Integer> lines) {
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("Null or empty station name ");
        }
        if(lines == null || lines.size()==0){
            throw new IllegalArgumentException("Null or empty list of lines name ");
        }
        this.name = name;
        this.lines = lines;
    }

    /**
     * A simple getter .
     * @return a string name of station .
     */
    public String getName() {
        return name;
    }

    /**
     * A simple getter .
     * @return the list of lines that belongs to that station name .
     */
    public List<Integer> getLines() {
        return lines;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StationView)) return false;

        StationView that = (StationView) o;

        if (!getName().equals(that.getName())) return false;
        return getLines().equals(that.getLines());
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getLines().hashCode();
        return result;
    }
}
