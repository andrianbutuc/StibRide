package andrian.stibride.model.db.jdbc;

import andrian.stibride.model.db.dto.StopDto;
import andrian.stibride.model.db.dto.StopsKey;
import andrian.stibride.model.db.exeception.RepositoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StopDao implements Dao<StopsKey, StopDto> {
    private final Connection connection;

    private StopDao() throws RepositoryException {
        this.connection = DBManager.getInstance().getConnection();
    }

    public static StopDao getInstance() throws RepositoryException {
        return StopDaoHolder.getInstance();
    }

    @Override
    public StopsKey insert(StopDto item) throws RepositoryException {
        throw new RepositoryException("not implemented");
    }

    @Override
    public void delete(StopsKey key) throws RepositoryException {
        throw new RepositoryException("not implemented");
    }

    @Override
    public void update(StopDto item) throws RepositoryException {
        throw new RepositoryException("not implemented");
    }

    @Override
    public List<StopDto> selectAll() throws RepositoryException {
        String sql = "SELECT * FROM STOPS";
        List<StopDto> stopDtos = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                StopDto dto = new StopDto(new StopsKey(rs.getInt("id_line"), rs.getInt("id_station")), rs.getInt("id_order"));
                stopDtos.add(dto);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return stopDtos;

    }

    @Override
    public StopDto select(StopsKey key) throws RepositoryException {
        if (key == null) {
            throw new RepositoryException("Aucune clé donnée en paramètre");
        }
        String sql = "SELECT * FROM STOPS WHERE id_line = ? and id_station = ?";
        StopDto dto = null;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, key.getIdLine());
            pstmt.setInt(2, key.getIdStation());
            ResultSet rs = pstmt.executeQuery();

            int count = 0;
            while (rs.next()) {
                dto = new StopDto(new StopsKey(rs.getInt("id_line"), rs.getInt("id_station")), rs.getInt("id_order"));
                count++;
            }
            if (count > 1) {
                throw new RepositoryException("Record pas unique " + key);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return dto;
    }

    /**
     * Returns a list of key of neighbors that belongs to that stops key .
     * @param key a stops key .
     * @return list of key of neighbors that belongs to that stops key .
     * @throws RepositoryException a repository exception .
     */
    public List<Integer> selectNeighbors(StopsKey key) throws RepositoryException {
        if (key == null) {
            throw new RepositoryException("Aucune clé donnée en paramètre");
        }
        StopDto dto = select(key);
        String sql = "SELECT id_station from STOPS join STATIONS on STOPS.id_station = STATIONS.id where (id_order = ? or id_order = ?) and id_line=?";
        List<Integer> neighbors = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, dto.getIdOrder()-1);
            pstmt.setInt(2, dto.getIdOrder()+1);
            pstmt.setInt(3, key.getIdLine());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                neighbors.add(rs.getInt("id_station"));
            }

        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return neighbors;
    }

    /**
     * Returns a list of lines that belongs to that station id .
     * @param id an integer .
     * @return a list of lines that belongs to that station id .
     * @throws RepositoryException a repository exception .
     */
    public List<Integer> selectLines(Integer id) throws RepositoryException {
        if(id ==null){
            throw new RepositoryException("Aucune clé donnée en paramètre");
        }
        String sql = "SELECT id_line from STOPS where id_station = ?";
        List<Integer> lines = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
               lines.add(rs.getInt(1));
            }

        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return lines;
    }

    private static class StopDaoHolder {
        private static StopDao getInstance() throws RepositoryException {
            return new StopDao();
        }
    }
}
