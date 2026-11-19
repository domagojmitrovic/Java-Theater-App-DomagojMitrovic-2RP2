package hr.algebra.repository.sql.mapper;

import hr.algebra.model.Country;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class CountryRowMapper implements RowMapper<Country> {

    public static final CountryRowMapper INSTANCE = new CountryRowMapper();

    private CountryRowMapper() {
    }

    @Override
    public Country map(ResultSet rs) throws SQLException {
        return Country.builder()
                .id(rs.getLong("id"))
                .countryCode(rs.getString("country_code"))
                .stateName(rs.getString("state_name"))
                .build();
    }

    /** Maps a country row from a join where the id column is aliased as {@code country_id}. */
    static Country mapJoined(ResultSet rs) throws SQLException {
        return Country.builder()
                .id(rs.getLong("country_id"))
                .countryCode(rs.getString("country_code"))
                .stateName(rs.getString("state_name"))
                .build();
    }
}
