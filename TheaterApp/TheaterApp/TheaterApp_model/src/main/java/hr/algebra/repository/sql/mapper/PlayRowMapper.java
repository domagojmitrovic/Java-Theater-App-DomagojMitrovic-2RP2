package hr.algebra.repository.sql.mapper;

import hr.algebra.model.DirectionStyle;
import hr.algebra.model.Play;
import hr.algebra.model.Theater;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class PlayRowMapper implements RowMapper<Play> {

    public static final PlayRowMapper INSTANCE = new PlayRowMapper();

    private PlayRowMapper() {
    }

    @Override
    public Play map(ResultSet rs) throws SQLException {
        return Play.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .director(DirectorRowMapper.mapJoined(rs))
                .theater(mapTheater(rs))
                .premierDate(rs.getObject("premier_date", LocalDate.class))
                .performanceCounter(rs.getInt("performance_counter"))
                .playType(DirectionStyle.valueOf(rs.getString("play_type")))
                .build();
    }

    private Theater mapTheater(ResultSet rs) throws SQLException {
        return Theater.builder()
                .id(rs.getLong("theater_id"))
                .name(rs.getString("theater_name"))
                .address(rs.getString("theater_address"))
                .foundedYear(rs.getInt("theater_founded_year"))
                .auditoriumCapacity(rs.getInt("theater_auditorium_capacity"))
                .history(rs.getString("theater_history"))
                .imagePath(rs.getString("theater_image_path"))
                .country(CountryRowMapper.mapJoined(rs))
                .city(CityRowMapper.mapJoined(rs))
                .build();
    }
}
