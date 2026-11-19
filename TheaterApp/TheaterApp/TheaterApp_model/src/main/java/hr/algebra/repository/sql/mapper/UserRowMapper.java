package hr.algebra.repository.sql.mapper;

import hr.algebra.model.Role;
import hr.algebra.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class UserRowMapper implements RowMapper<User> {

    public static final UserRowMapper INSTANCE = new UserRowMapper();

    private UserRowMapper() {
    }

    @Override
    public User map(ResultSet rs) throws SQLException {
        return User.builder()
                .id(rs.getLong("id"))
                .username(rs.getString("username"))
                .passwordHash(rs.getString("password_hash"))
                .role(Role.valueOf(rs.getString("role")))
                .build();
    }
}
