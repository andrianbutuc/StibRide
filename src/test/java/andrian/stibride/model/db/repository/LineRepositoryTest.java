package andrian.stibride.model.db.repository;

import andrian.stibride.model.db.dto.LineDto;
import andrian.stibride.model.db.exeception.RepositoryException;
import andrian.stibride.model.db.jdbc.LineDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
/**
 * Suite à notre conversation de 11/05/2021 au séance de réponse aux questions où j'ai demandé s'il faut tester tous
 * les classes repository j'ai reçu comme réponse qu'il suffit testé un seul repository et donc j'avais pris la décision
 * de tester 2 repository celui-là et le repository 'favorite' .
 */
class LineRepositoryTest {
    @Mock
    private LineDao mock;
    private static final int KEY = 1;
    private static final int KEYINVALIDE = 3;
    private final LineDto line;
    private final List<LineDto> all;


    public LineRepositoryTest() {
        System.out.println("LineRepositoryTest Constructor");
        //Test data
        all = new ArrayList<>();
        line = new LineDto(KEY);
        all.add(line);
        all.add(new LineDto(2));
        all.add(new LineDto(5));
        all.add(new LineDto(6));
    }


    @BeforeEach
    void init() throws RepositoryException {
        System.out.println("==== BEFORE EACH =====");
        //Mock behaviour
        Mockito.lenient().when(mock.select(KEY)).thenReturn(line);
        Mockito.lenient().when(mock.select(KEYINVALIDE)).thenReturn(null);
        Mockito.lenient().when(mock.selectAll()).thenReturn(all);
        Mockito.lenient().when(mock.select(null)).thenThrow(RepositoryException.class);
    }

    @Test
    void getAll() throws RepositoryException {
        LineRepository repository = new LineRepository(mock);
        List<LineDto> result = repository.getAll();
        assertArrayEquals(result.toArray(),all.toArray());
        Mockito.verify(mock, times(1)).selectAll();
    }

    @Test
    void get() throws RepositoryException {
        LineDto expected = line ;
        LineRepository repository = new LineRepository(mock);

        LineDto result = repository.get(KEY);
        assertEquals(expected, result);
        Mockito.verify(mock, times(1)).select(KEY);
    }
    @Test
    void getNotExist() throws RepositoryException {
        LineRepository repository = new LineRepository(mock);

        LineDto result = repository.get(KEYINVALIDE);
        assertNull( result);
        Mockito.verify(mock, times(1)).select(KEYINVALIDE);
    }
    @Test
    void getIncorrectParam() throws RepositoryException {
        LineRepository repository = new LineRepository(mock);
        Integer incorrect = null;
        assertThrows(RepositoryException.class, () -> {
            //Action
            repository.get(incorrect);
        });
        Mockito.verify(mock, times(1)).select(null);
    }

}