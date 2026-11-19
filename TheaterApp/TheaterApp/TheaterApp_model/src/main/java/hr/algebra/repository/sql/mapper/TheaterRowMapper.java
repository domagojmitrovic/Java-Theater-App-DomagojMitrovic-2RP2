package hr.algebra.repository.sql.mapper;

import hr.algebra.model.Theater;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class TheaterRowMapper implements RowMapper<Theater> {

    public static final TheaterRowMapper INSTANCE = new TheaterRowMapper();

    private TheaterRowMapper() {
    }

    @Override
    public Theater map(ResultSet rs) throws SQLException {
        return Theater.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .address(rs.getString("address"))
                .foundedYear(rs.getInt("founded_year"))
                .auditoriumCapacity(rs.getInt("auditorium_capacity"))
                .history(rs.getString("history"))
                .imagePath(rs.getString("image_path"))
                .country(CountryRowMapper.mapJoined(rs))
                .city(CityRowMapper.mapJoined(rs))
                .build();
    }
}
