package andrian.stibride.model.db.repository;

import andrian.stibride.model.db.dto.StopDto;
import andrian.stibride.model.db.dto.StopsKey;
import andrian.stibride.model.db.exeception.RepositoryException;
import andrian.stibride.model.db.jdbc.StopDao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StopRepository implements Repository<StopsKey, StopDto>{
    private final StopDao dao;

    public StopRepository()throws RepositoryException {
        dao = StopDao.getInstance();
    }
    StopRepository(StopDao stopDao){
        this.dao=stopDao;
    }

    @Override
    public StopsKey add(StopDto item) throws RepositoryException {
        StopsKey key = item.getKey();
        if(contains(key)){
            dao.update(item);
        }else {
            key = dao.insert(item);
        }

        return key;
    }

    @Override
    public void remove(StopsKey key) throws RepositoryException {
        dao.delete(key);
    }

    @Override
    public List<StopDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    @Override
    public StopDto get(StopsKey key) throws RepositoryException {
        return dao.select(key);
    }

    @Override
    public boolean contains(StopsKey key) throws RepositoryException {
        return dao.select(key) != null;
    }

    /**
     * Returns a list of lines id that belongs to that key .
     *
     * @param key an integer .
     * @return a list of lines id that belongs to that key .
     * @throws RepositoryException a repository exception .
     */
    public List<Integer> getLinesOfStation(Integer key) throws RepositoryException {
        return dao.selectLines(key);
    }

    /**
     * Returns a set of key of neighbors that belongs to that key .
     *
     * @param key an integer .
     * @return a set of key of neighbors that belongs to that key .
     * @throws RepositoryException a repository exception .
     */
    public Set<Integer> getAllNeighbors(Integer key) throws RepositoryException {
        var l = getLinesOfStation(key);
        Set<Integer> neighbors = new HashSet<>();
        for (Integer line : l) {
            StopsKey id = new StopsKey(line, key);
            var lineNeighbors = dao.selectNeighbors(id);
            neighbors.addAll(lineNeighbors);
        }
        return neighbors;
    }

}
