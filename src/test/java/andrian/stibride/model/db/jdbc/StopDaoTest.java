package andrian.stibride.model.db.jdbc;

import andrian.stibride.model.db.dto.StopDto;
import andrian.stibride.model.db.dto.StopsKey;
import andrian.stibride.model.db.exeception.RepositoryException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StopDaoTest {

    @Test
    void selectAll() throws RepositoryException {
        StopDao instance = StopDao.getInstance();
        List<StopDto> result = instance.selectAll();

        assertEquals(result.size(),94);

    }

    @Test
    void selectExist() throws RepositoryException {
        StopDao instance = StopDao.getInstance();

        StopDto expected = new StopDto(new StopsKey(1,8382),1);
        StopDto result = instance.select(new StopsKey(1,8382));
        assertEquals(expected,result);
    }

    @Test
    void selectNotExist() throws RepositoryException {
        StopDao instance = StopDao.getInstance();

        StopDto result = instance.select(new StopsKey(1,8381));
        assertNull(result);
    }
    @Test
    void selectIncorrectParameter() throws RepositoryException {
        StopDao instance = StopDao.getInstance();
        StopsKey id = null ;
        assertThrows(RepositoryException.class, () -> {
            //Action
            instance.select(id);
        });

    }
    @Test
    void selectNeighborsExist() throws RepositoryException {
        StopDao instance = StopDao.getInstance();

        List<Integer> expected = new ArrayList<>();
        expected.add(8422);
        expected.add(8442);
        List<Integer> result = instance.selectNeighbors(new StopsKey(6,8432));
        assertArrayEquals(expected.toArray(),result.toArray());
    }
    @Test
    void selectNeighborsNotExist() throws RepositoryException {
        StopDao instance = StopDao.getInstance();

        assertThrows(NullPointerException.class, () -> {
            //Action
            instance.selectNeighbors(new StopsKey(6,843));
        });
    }
    @Test
    void selectNeighborsIncorrectParameter() throws RepositoryException {
        StopDao instance = StopDao.getInstance();
        StopsKey id = null ;
        assertThrows(RepositoryException.class, () -> {
            //Action
            instance.selectNeighbors(id);
        });
    }

    @Test
    void selectLinesExist() throws RepositoryException {
        StopDao instance = StopDao.getInstance();
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(5);
        expected.add(6);

        List<Integer> result = instance.selectLines(8382);
        assertArrayEquals(expected.toArray(),result.toArray());
    }
    @Test
    void selectLinesNotExist() throws RepositoryException {
        StopDao instance = StopDao.getInstance();

        List<Integer> result = instance.selectLines(838);
        assertEquals(0,result.size());
    }
    @Test
    void selectLinesIncorrectParameter() throws RepositoryException {
        StopDao instance = StopDao.getInstance();
        Integer id = null;
        assertThrows(RepositoryException.class, () -> {
            //Action
            instance.selectLines(id);
        });
    }
}