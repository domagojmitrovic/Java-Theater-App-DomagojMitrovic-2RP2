package hr.algebra.repository.sql.mapper;

import hr.algebra.model.City;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class CityRowMapper implements RowMapper<City> {

    public static final CityRowMapper INSTANCE = new CityRowMapper();

    private CityRowMapper() {
    }

    @Override
    public City map(ResultSet rs) throws SQLException {
        return City.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .postalCode(rs.getString("postal_code"))
                .build();
    }

    /** Maps a city row from a join where the columns are aliased as {@code city_id} and {@code city_name}. */
    static City mapJoined(ResultSet rs) throws SQLException {
        return City.builder()
                .id(rs.getLong("city_id"))
                .name(rs.getString("city_name"))
                .postalCode(rs.getString("postal_code"))
                .build();
    }
}
