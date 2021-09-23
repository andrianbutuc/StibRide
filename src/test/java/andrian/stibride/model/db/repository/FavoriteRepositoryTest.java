package andrian.stibride.model.db.repository;

import andrian.stibride.model.db.dto.FavoriteDto;
import andrian.stibride.model.db.dto.StationDto;
import andrian.stibride.model.db.exeception.RepositoryException;
import andrian.stibride.model.db.jdbc.FavoriteDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class FavoriteRepositoryTest {

    @Mock
    private FavoriteDao mock;
    private static final int KEY = 1;
    private static final int KEYINVALIDE = -1;
    private final FavoriteDto line;
    private final FavoriteDto tmp;
    private final List<FavoriteDto> all;
    public FavoriteRepositoryTest() {
        System.out.println("LineRepositoryTest Constructor");
        //Test data
        all = new ArrayList<>();
        line = new FavoriteDto(KEY,"test1",new StationDto(8322,"LOUISE"),new StationDto(8432,"ROGIER"));
        tmp = new FavoriteDto(-1,"testadd",new StationDto(8022,"GARE CENTRALE"),new StationDto(8042,"ARTS-LOI"));
        all.add(line);
        all.add(new FavoriteDto(2,"test2",new StationDto(8022,"GARE CENTRALE"),new StationDto(8042,"ARTS-LOI")));
        all.add(new FavoriteDto(3,"test3",new StationDto(8042,"ARTS-LOI"),new StationDto(8072,"MERODE")));
    }
    @BeforeEach
    void init() throws RepositoryException {
        System.out.println("==== BEFORE EACH =====");
        //Mock behaviour
        Mockito.lenient().when(mock.selectAll()).thenReturn(all);
        Mockito.lenient().when(mock.select(KEY)).thenReturn(line);
        Mockito.lenient().when(mock.select(KEYINVALIDE)).thenReturn(null);
        Mockito.lenient().when(mock.select(null)).thenThrow(RepositoryException.class);
    }

    @Test
    void getAll() throws RepositoryException {
        FavoriteRepository repository = new FavoriteRepository(mock);
        List<FavoriteDto> result = repository.getAll();
        assertArrayEquals(result.toArray(),all.toArray());
        Mockito.verify(mock, times(1)).selectAll();
    }

    @Test
    void get() throws RepositoryException {
        FavoriteDto expected = line ;
        FavoriteRepository repository = new FavoriteRepository(mock);
        FavoriteDto result = repository.get(KEY);
        assertEquals(expected, result);
        Mockito.verify(mock, times(1)).select(KEY);
    }
    @Test
    void getNotExist() throws RepositoryException {
        FavoriteRepository repository = new FavoriteRepository(mock);
        FavoriteDto result = repository.get(KEYINVALIDE);
        assertNull( result);
        Mockito.verify(mock, times(1)).select(KEYINVALIDE);
    }
    @Test
    void getIncorrectParam() throws RepositoryException {
        FavoriteRepository repository = new FavoriteRepository(mock);
        Integer incorrect = null;
        assertThrows(RepositoryException.class, () -> {
            //Action
            repository.get(incorrect);
        });
        Mockito.verify(mock, times(0)).select(null);
    }
    @Test
    void addExisting() throws RepositoryException {
        System.out.println("testAddWhenExisting");
        //Arrange
        FavoriteRepository repository = new FavoriteRepository(mock);
        //Action
        repository.add(line);
        //Assert
        Mockito.verify(mock, times(1)).select(KEY);
        Mockito.verify(mock, times(1)).update(line);
        Mockito.verify(mock, times(0)).insert(any(FavoriteDto.class));
    }
    @Test
    void addNotExisting() throws RepositoryException {
        System.out.println("testAddWhenExisting");
        //Arrange
        FavoriteRepository repository = new FavoriteRepository(mock);
        //Action
        repository.add(tmp);
        //Assert
        Mockito.verify(mock, times(1)).select(tmp.getKey());
        Mockito.verify(mock, times(0)).update(tmp);
        Mockito.verify(mock, times(1)).insert(any(FavoriteDto.class));
    }

    @Test
    void addIncorrectParam() throws RepositoryException {
        System.out.println("testAddWhenExisting");
        //Arrange
        FavoriteRepository repository = new FavoriteRepository(mock);
        FavoriteDto incorrect = null ;
        //Action
        assertThrows(RepositoryException.class, () -> {
            //Action
            repository.add(incorrect);
        });
        //Assert
        Mockito.verify(mock, times(0)).select(null);
        Mockito.verify(mock, times(0)).update(tmp);
        Mockito.verify(mock, times(0)).insert(any(FavoriteDto.class));
    }

    @Test
    void remove() throws RepositoryException {
        FavoriteRepository repository = new FavoriteRepository(mock);
        repository.remove(KEY);
        //Assert
        Mockito.verify(mock, times(1)).delete(KEY);
    }
    @Test
    void removeNotExisting() throws RepositoryException {
        FavoriteRepository repository = new FavoriteRepository(mock);
        repository.remove(KEYINVALIDE);
        //Assert
        Mockito.verify(mock, times(1)).delete(KEYINVALIDE);
    }
    @Test
    void removeIncorrectParam() throws RepositoryException {
        FavoriteRepository repository = new FavoriteRepository(mock);
        Integer incorrect = null;
        assertThrows(RepositoryException.class, () -> {
            //Action
            repository.remove(incorrect);
        });
        Mockito.verify(mock, times(0)).delete(incorrect);
    }


    @Test
    void contains() throws RepositoryException {
        FavoriteRepository repository = new FavoriteRepository(mock);
        boolean expected = true;
        boolean result = repository.contains(KEY);
        //Assert
        assertEquals(expected, result);
        Mockito.verify(mock, times(1)).select(KEY);
    }
    @Test
    void containsKeyDontExist() throws RepositoryException {
        FavoriteRepository repository = new FavoriteRepository(mock);
        boolean expected = false;
        boolean result = repository.contains(KEYINVALIDE);
        //Assert
        assertEquals(expected, result);
        Mockito.verify(mock, times(1)).select(KEYINVALIDE);
    }
    @Test
    void containsKeyNull() throws RepositoryException {
        FavoriteRepository repository = new FavoriteRepository(mock);
        assertThrows(RepositoryException.class, () -> {
            //Action
            repository.contains(null);
        });
        Mockito.verify(mock, times(0)).select(null);

    }
}