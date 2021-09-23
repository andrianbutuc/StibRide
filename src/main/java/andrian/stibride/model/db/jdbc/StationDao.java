package andrian.stibride.model.db.jdbc;

import andrian.stibride.model.db.dto.StationDto;
import andrian.stibride.model.db.exeception.RepositoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StationDao implements Dao<Integer, StationDto> {
    private final Connection connection ;

    private StationDao()throws RepositoryException {
        this.connection = DBManager.getInstance().getConnection();
    }
    public static StationDao getInstance()throws RepositoryException{
        return StationDaoHolder.getInstance();
    }

    @Override
    public Integer insert(StationDto item) throws RepositoryException {
        throw new RepositoryException("not implemented");
    }

    @Override
    public void delete(Integer key) throws RepositoryException {
        throw new RepositoryException("not implemented");
    }

    @Override
    public void update(StationDto item) throws RepositoryException {
        throw new RepositoryException("not implemented");
    }

    @Override
    public List<StationDto> selectAll() throws RepositoryException {
        String sql = "SELECT * FROM STATIONS";
        List<StationDto> stationDtos = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                StationDto dto = new StationDto(rs.getInt("id"),rs.getString("name"));
                stationDtos.add(dto);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return stationDtos;
    }

    @Override
    public StationDto select(Integer key) throws RepositoryException {
        if (key == null) {
            throw new RepositoryException("Aucune clé donnée en paramètre");
        }
        String sql = "SELECT * FROM STATIONS WHERE  id = ?";
        StationDto dto = null;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, key);
            ResultSet rs = pstmt.executeQuery();

            int count = 0;
            while (rs.next()) {
                dto = new StationDto(rs.getInt("id"), rs.getString("name"));
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
    private static class StationDaoHolder{
        private static StationDao getInstance()throws RepositoryException{
            return new StationDao();
        }
    }
}
