package andrian.stibride.model.db.jdbc;

import andrian.stibride.model.db.dto.FavoriteDto;
import andrian.stibride.model.db.dto.StationDto;
import andrian.stibride.model.db.exeception.RepositoryException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FavoriteDaoTest {

    @Test
    void insert() throws RepositoryException {
        FavoriteDao instance = FavoriteDao.getInstance();
        FavoriteDto dto = new FavoriteDto(-1,"test4",new StationDto(8142,"ALMA"),new StationDto(8232,"DELTA"));
        Integer result = instance.insert(dto);
        assertTrue(result>0);
    }
    @Test
    void insertNullFav() throws RepositoryException {
        FavoriteDao instance = FavoriteDao.getInstance();
        FavoriteDto dto =null;
        assertThrows(RepositoryException.class, () -> instance.insert(dto));
    }

    @Test
    void deleteFavoriteExiste() throws RepositoryException {
        FavoriteDao instance = FavoriteDao.getInstance();
        Integer id = 3 ;
        assertDoesNotThrow(()-> instance.delete(id));
    }
    @Test
    void deleteFavoriteNotExiste() throws RepositoryException {
        FavoriteDao instance = FavoriteDao.getInstance();
        Integer id = -1 ;
        assertDoesNotThrow(()-> instance.delete(id));
    }
    @Test
    void deleteFavoriteNull() throws RepositoryException {
        FavoriteDao instance = FavoriteDao.getInstance();
        Integer id = null ;
        assertThrows(RepositoryException.class, () -> instance.delete(id));
    }

    @Test
    void update() throws RepositoryException {
        FavoriteDao instance = FavoriteDao.getInstance();
        FavoriteDto dto = new FavoriteDto(1,"test4",new StationDto(8142,"ALMA"),new StationDto(8232,"DELTA"));
        instance.update(dto);
        assertEquals(dto,instance.select(1));

    }

    @Test
    void updateFavoriteAbsent() throws RepositoryException {
        FavoriteDao instance = FavoriteDao.getInstance();
        FavoriteDto dto = new FavoriteDto(-2,"test4",new StationDto(8142,"ALMA"),new StationDto(8232,"DELTA"));
        instance.update(dto);
        assertNull(instance.select(-2));

    }
    @Test
    void updateNullFav() throws RepositoryException {
        FavoriteDao instance = FavoriteDao.getInstance();
        FavoriteDto dto = null;
        assertThrows(RepositoryException.class, () -> instance.update(dto));
    }

    @Test
    void selectAll() throws RepositoryException {
        FavoriteDao instance = FavoriteDao.getInstance();
        List<FavoriteDto> result = instance.selectAll();
        assertEquals(result.size(),3);
    }

    @Test
    void select() throws RepositoryException {
        FavoriteDao instance = FavoriteDao.getInstance();
        Integer id = 2 ;
        FavoriteDto expected = new FavoriteDto(2,"test2",new StationDto(8022,"GARE CENTRALE"),new StationDto(8042,"ARTS-LOI"));
        FavoriteDto result = instance.select(id);
        assertEquals(expected,result);
    }
    @Test
    void selectNotExist() throws RepositoryException {
        FavoriteDao instance = FavoriteDao.getInstance();
        Integer id = 2 ;
        FavoriteDto expected = new FavoriteDto(50,"test2",new StationDto(8022,"GARE CENTRALE"),new StationDto(8042,"ARTS-LOI"));
        FavoriteDto result = instance.select(id);
        assertNotSame(expected,result);
    }
    @Test
    void selectNullId() throws RepositoryException {
        FavoriteDao instance = FavoriteDao.getInstance();
        Integer id = null ;
        assertThrows(RepositoryException.class, () -> instance.select(id));
    }
}