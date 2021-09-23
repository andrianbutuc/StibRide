package andrian.stibride.model.db.repository;

import andrian.stibride.model.db.dto.FavoriteDto;
import andrian.stibride.model.db.exeception.RepositoryException;
import andrian.stibride.model.db.jdbc.FavoriteDao;

import java.util.List;

public class FavoriteRepository implements Repository<Integer, FavoriteDto> {
    private final FavoriteDao dao ;

    public FavoriteRepository() throws RepositoryException {
        this.dao = FavoriteDao.getInstance();
    }

    FavoriteRepository(FavoriteDao dao){ this.dao = dao;}

    @Override
    public Integer add(FavoriteDto item) throws RepositoryException {
        if(item == null){
            throw new RepositoryException("Favorite : null");
        }
        Integer key = item.getKey();
        if(contains(key)){
            dao.update(item);
        }else {
            key = dao.insert(item);
        }
        return key;
    }

    @Override
    public void remove(Integer key) throws RepositoryException {
        if(key == null){
            throw new RepositoryException("Favorite : null");
        }
        dao.delete(key);
    }

    @Override
    public List<FavoriteDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    @Override
    public FavoriteDto get(Integer key) throws RepositoryException {
        if(key == null){
            throw new RepositoryException("Favorite : null");
        }
        return dao.select(key);
    }

    @Override
    public boolean contains(Integer key) throws RepositoryException {
        if(key == null){
            throw new RepositoryException("Favorite : null");
        }
        return dao.select(key) != null;
    }
}
