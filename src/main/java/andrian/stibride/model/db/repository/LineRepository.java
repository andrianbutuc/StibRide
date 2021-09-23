package andrian.stibride.model.db.repository;

import andrian.stibride.model.db.dto.LineDto;
import andrian.stibride.model.db.exeception.RepositoryException;
import andrian.stibride.model.db.jdbc.LineDao;

import java.util.List;

public class LineRepository implements Repository<Integer, LineDto> {
    private final LineDao dao;
    public LineRepository()throws RepositoryException{
        dao = LineDao.getInstance();
    }
    LineRepository(LineDao dao){
        this.dao= dao;
    }
    @Override
    public Integer add(LineDto item) throws RepositoryException {
        Integer key = item.getKey();
        if(!contains(key)){
            key = dao.insert(item);
        }
        return key;
    }

    @Override
    public void remove(Integer key) throws RepositoryException {
        dao.delete(key);
    }

    @Override
    public List<LineDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    @Override
    public LineDto get(Integer key) throws RepositoryException {
        return dao.select(key);
    }

    @Override
    public boolean contains(Integer key) throws RepositoryException {
        return dao.select(key) != null;
    }
}
