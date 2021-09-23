package andrian.stibride.model.db.repository;

import andrian.stibride.model.db.dto.StationDto;
import andrian.stibride.model.db.exeception.RepositoryException;
import andrian.stibride.model.db.jdbc.StationDao;

import java.util.List;

public class StationRepository implements Repository<Integer, StationDto>{
    private final StationDao dao;

    public StationRepository()throws RepositoryException {
        dao = StationDao.getInstance();
    }
    StationRepository(StationDao dao){
        this.dao = dao;
    }
    @Override
    public Integer add(StationDto item) throws RepositoryException {
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
        dao.delete(key);
    }

    @Override
    public List<StationDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    @Override
    public StationDto get(Integer key) throws RepositoryException {
        return dao.select(key);
    }

    @Override
    public boolean contains(Integer key) throws RepositoryException {
        return dao.select(key) != null;
    }
}
