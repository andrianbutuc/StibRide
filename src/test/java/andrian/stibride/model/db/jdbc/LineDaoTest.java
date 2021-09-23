package andrian.stibride.model.db.jdbc;

import andrian.stibride.model.db.dto.LineDto;
import andrian.stibride.model.db.exeception.RepositoryException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LineDaoTest {

    @Test
    void selectAll() throws RepositoryException {
        LineDao lineDao = LineDao.getInstance();
        List<LineDto> expected = new ArrayList<>();
        expected.add(new LineDto(1));
        expected.add(new LineDto(2));
        expected.add(new LineDto(5));
        expected.add(new LineDto(6));
        List<LineDto> result = lineDao.selectAll();

        assertArrayEquals(expected.toArray(),result.toArray());

    }

    @Test
    void selectExist() throws RepositoryException {
        LineDao lineDao = LineDao.getInstance();
        LineDto expected = new LineDto(5);
        LineDto result = lineDao.select(5);
        assertEquals(expected,result);
    }

    @Test
    void selectNotExist() throws RepositoryException {
        LineDao lineDao = LineDao.getInstance();
        LineDto result = lineDao.select(4);
        assertNull(result);
    }
    @Test
    void selectIncorrectParameter() throws RepositoryException {
        LineDao lineDao = LineDao.getInstance();
        Integer id = null ;
        assertThrows(RepositoryException.class, () -> {
            //Action
            lineDao.select(id);
        });

    }

}