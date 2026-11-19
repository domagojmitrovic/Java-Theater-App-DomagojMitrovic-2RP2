package hr.algebra.repository.sql.mapper;

import hr.algebra.model.DirectionStyle;
import hr.algebra.model.Director;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class DirectorRowMapper implements RowMapper<Director> {

    public static final DirectorRowMapper INSTANCE = new DirectorRowMapper();

    private DirectorRowMapper() {
    }

    @Override
    public Director map(ResultSet rs) throws SQLException {
        return Director.builder()
                .id(rs.getLong("id"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .oib(rs.getString("oib"))
                .directionStyle(toDirectionStyle(rs.getString("direction_style")))
                .directorId(rs.getString("director_id"))
                .build();
    }

    /** Maps a director row from a join where the columns are prefixed with {@code director_}. */
    static Director mapJoined(ResultSet rs) throws SQLException {
        return Director.builder()
                .id(rs.getLong("director_id"))
                .firstName(rs.getString("director_first_name"))
                .lastName(rs.getString("director_last_name"))
                .oib(rs.getString("director_oib"))
                .directionStyle(toDirectionStyle(rs.getString("director_direction_style")))
                .directorId(rs.getString("director_code"))
                .build();
    }

    static DirectionStyle toDirectionStyle(String value) {
        return value == null ? null : DirectionStyle.valueOf(value);
    }
}
