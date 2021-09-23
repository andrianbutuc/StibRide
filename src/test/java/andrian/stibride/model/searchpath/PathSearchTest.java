package andrian.stibride.model.searchpath;

import andrian.stibride.model.db.dto.StationDto;
import andrian.stibride.model.db.exeception.RepositoryException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PathSearchTest {

    @Test
    void getShortestPathBetween() throws RepositoryException {
        PathSearch pathSearch = new PathSearch();
        StationDto orgin = new StationDto(8032,"PARC");
        StationDto destination = new StationDto(8042,"ARTS-LOI");
        List<StationView> expected = new ArrayList<>();
        List<Integer> lines1 = new ArrayList<>();
        lines1.add(1);
        lines1.add(5);
        expected.add(new StationView("PARC",lines1));
        List<Integer> lines2 = new ArrayList<>();
        lines2.add(1);
        lines2.add(2);
        lines2.add(5);
        lines2.add(6);
        expected.add(new StationView("ARTS-LOI",lines2));
        List<StationView> result = pathSearch.getShortestPathBetween(orgin,destination);
        assertEquals(expected,result);
    }
    @Test
    void getShortestPathBetweenOriginIncorrect() throws RepositoryException {
        PathSearch pathSearch = new PathSearch();
        StationDto orgin = new StationDto(803,"PARC");
        StationDto destination = new StationDto(8042,"ARTS-LOI");
        assertThrows(NullPointerException.class,()->pathSearch.getShortestPathBetween(orgin,destination));
    }
    @Test
    void getShortestPathBetweenDestinationIncorrect() throws RepositoryException {
        PathSearch pathSearch = new PathSearch();
        StationDto orgin = new StationDto(8032,"PARC");
        StationDto destination = new StationDto(802,"ARTS-LOI");
        assertThrows(NullPointerException.class,()->pathSearch.getShortestPathBetween(orgin,destination));
    }
    @Test
    void getShortestPathBetweenOriginNull() throws RepositoryException {
        PathSearch pathSearch = new PathSearch();
        StationDto orgin = null;
        StationDto destination = new StationDto(8042,"ARTS-LOI");
       assertThrows(RepositoryException.class,()->pathSearch.getShortestPathBetween(orgin,destination));
    }
    @Test
    void getShortestPathBetweenDestinationNull() throws RepositoryException {
        PathSearch pathSearch = new PathSearch();
        StationDto orgin = new StationDto(8032,"PARC");
        StationDto destination = null;
        assertThrows(RepositoryException.class,()->pathSearch.getShortestPathBetween(orgin,destination));
    }
    @Test
    void getShortestPathBetweenOriginAndDestinationEquals() throws RepositoryException {
        PathSearch pathSearch = new PathSearch();
        StationDto orgin = new StationDto(8032,"PARC");
        StationDto destination = new StationDto(8032,"PARC");
        List<StationView> expected = new ArrayList<>();
        List<Integer> lines1 = new ArrayList<>();
        lines1.add(1);
        lines1.add(5);
        expected.add(new StationView("PARC",lines1));
        List<StationView> result = pathSearch.getShortestPathBetween(orgin,destination);
        assertEquals(expected,result);
    }
}