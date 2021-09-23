package andrian.stibride.model.db.jdbc;

import andrian.stibride.model.db.dto.StationDto;
import andrian.stibride.model.db.exeception.RepositoryException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StationDaoTest {

    @Test
    void selectAll() throws RepositoryException {
        StationDao instance = StationDao.getInstance();
        List<StationDto> result = instance.selectAll();

        assertEquals(result.size(),60);

    }

    @Test
    void selectExist() throws RepositoryException {
        StationDao instance = StationDao.getInstance();

        StationDto expected = new StationDto(8032,"PARC");
        StationDto result = instance.select(8032);
        assertEquals(expected,result);
    }

    @Test
    void selectNotExist() throws RepositoryException {
        StationDao instance = StationDao.getInstance();

        StationDto result = instance.select(803);
        assertNull(result);
    }
    @Test
    void selectIncorrectParameter() throws RepositoryException {
        StationDao instance = StationDao.getInstance();
        Integer id = null ;
        assertThrows(RepositoryException.class, () -> {
            //Action
            instance.select(id);
        });

    }
}