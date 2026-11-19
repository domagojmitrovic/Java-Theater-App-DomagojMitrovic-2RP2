package hr.algebra.repository.sql.mapper;

import hr.algebra.model.Actor;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class ActorRowMapper implements RowMapper<Actor> {

    /** The mapper is stateless, so a single shared instance is enough. */
    public static final ActorRowMapper INSTANCE = new ActorRowMapper();

    private ActorRowMapper() {
    }

    @Override
    public Actor map(ResultSet rs) throws SQLException {
        return Actor.builder()
                .id(rs.getLong("id"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .oib(rs.getString("oib"))
                .actorId(rs.getString("actor_id"))
                .build();
    }
}
