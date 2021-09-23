package andrian.stibride.model.db.jdbc;

import andrian.stibride.model.db.dto.FavoriteDto;
import andrian.stibride.model.db.dto.StationDto;
import andrian.stibride.model.db.exeception.RepositoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FavoriteDao implements Dao<Integer, FavoriteDto>{
    private final Connection connection;

    private FavoriteDao() throws RepositoryException {
        this.connection = DBManager.getInstance().getConnection();
    }
    public static FavoriteDao getInstance() throws RepositoryException {
        return FavoriteDaoHolder.getInstance();
    }
    @Override
    public Integer insert(FavoriteDto item) throws RepositoryException {
        if (item == null) {
            throw new RepositoryException("Aucune élément donné en paramètre");
        }
        DBManager.getInstance().startTransaction();
        int id = -1;
        String sql = "INSERT INTO FAVORITE(name,id_origin,id_destination) values(?, ? ,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, item.getName());
            pstmt.setInt(2, item.getOrigin().getKey());
            pstmt.setInt(3, item.getDestination().getKey());
            pstmt.executeUpdate();

            ResultSet result = pstmt.getGeneratedKeys();
            while (result.next()) {
                id = result.getInt(1);
            }
        } catch (SQLException e) {
            DBManager.getInstance().cancelTransaction();
            throw new RepositoryException(e);
        }
        DBManager.getInstance().validateTransaction();
        return id;
    }

    @Override
    public void delete(Integer key) throws RepositoryException {
        if (key == null) {
            throw new RepositoryException("Aucune clé donnée en paramètre");
        }
        DBManager.getInstance().startTransaction();
        String sql = "DELETE FROM FAVORITE WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, key);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            DBManager.getInstance().cancelTransaction();
            throw new RepositoryException(e);
        }
        DBManager.getInstance().validateTransaction();
    }

    @Override
    public void update(FavoriteDto item) throws RepositoryException {
        if (item == null) {
            throw new RepositoryException("Aucune élément donné en paramètre");
        }
        DBManager.getInstance().startTransaction();
        String sql = "UPDATE FAVORITE SET name=? ,id_origin=?,id_destination=? where id=? ";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, item.getName());
            pstmt.setInt(2, item.getOrigin().getKey());
            pstmt.setInt(3, item.getDestination().getKey());
            pstmt.setInt(4, item.getKey());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            DBManager.getInstance().cancelTransaction();
            throw new RepositoryException(e.getMessage());
        }
        DBManager.getInstance().validateTransaction();
    }

    @Override
    public List<FavoriteDto> selectAll() throws RepositoryException {
        String sql = "SELECT FAVORITE.id,FAVORITE.name ,s1.id,s1.name,s2.id, s2.name FROM FAVORITE " +
                "join STATIONS as s1 on FAVORITE.id_origin=s1.id " +
                "join STATIONS as s2 on FAVORITE.id_destination = s2.id ;";

        List<FavoriteDto> dtos = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                FavoriteDto dto = new FavoriteDto(rs.getInt(1),rs.getString(2),
                            new StationDto(rs.getInt(3),rs.getString(4)),
                            new StationDto(rs.getInt(5),rs.getString(6)));
                dtos.add(dto);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return dtos;
    }

    @Override
    public FavoriteDto select(Integer key) throws RepositoryException {
        if (key == null) {
            throw new RepositoryException("Aucune clé donnée en paramètre");
        }
        String sql = "SELECT FAVORITE.id,FAVORITE.name ,s1.id,s1.name,s2.id, s2.name FROM FAVORITE " +
                "join STATIONS as s1 on FAVORITE.id_origin=s1.id " +
                "join STATIONS as s2 on FAVORITE.id_destination = s2.id WHERE FAVORITE.id = ?";

        FavoriteDto dto = null;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, key);
            ResultSet rs = pstmt.executeQuery();
            int count = 0;
            while (rs.next()) {
                dto = new FavoriteDto(rs.getInt(1), rs.getString(2),
                        new StationDto(rs.getInt(3),rs.getString(4)),
                        new StationDto(rs.getInt(5),rs.getString(6)));
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
    private static class FavoriteDaoHolder {
        private static FavoriteDao getInstance() throws RepositoryException {
            return new FavoriteDao();
        }
    }

}
