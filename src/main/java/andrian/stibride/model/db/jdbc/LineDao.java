package andrian.stibride.model.db.jdbc;

import andrian.stibride.model.db.dto.LineDto;
import andrian.stibride.model.db.exeception.RepositoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LineDao implements Dao<Integer, LineDto> {
    private final Connection connection ;

    private LineDao() throws RepositoryException{
        this.connection = DBManager.getInstance().getConnection();
    }
    public static LineDao getInstance()throws RepositoryException{
        return LineDaoHolder.getInstance();
    }


    @Override
    public Integer insert(LineDto item) throws RepositoryException {
        throw new RepositoryException("not implemented");
    }

    @Override
    public void delete(Integer key) throws RepositoryException {
        throw new RepositoryException("not implemented");
    }

    @Override
    public void update(LineDto item) throws RepositoryException {
        throw new RepositoryException("not implemented");
    }

    @Override
    public List<LineDto> selectAll() throws RepositoryException {
        String sql = "SELECT * FROM LINES";
        List<LineDto> lineDtos = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                LineDto dto = new LineDto(rs.getInt(1));
                lineDtos.add(dto);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return lineDtos;

    }

    @Override
    public LineDto select(Integer key) throws RepositoryException {
        if (key == null) {
            throw new RepositoryException("Aucune clé donnée en paramètre");
        }
        String sql = "SELECT * FROM LINES WHERE  id = ?";
        LineDto dto = null;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, key);
            ResultSet rs = pstmt.executeQuery();

            int count = 0;
            while (rs.next()) {
                dto = new LineDto(rs.getInt(1));
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
    private static class LineDaoHolder{
        private static LineDao getInstance()throws RepositoryException{
            return new LineDao();
        }
    }
}
