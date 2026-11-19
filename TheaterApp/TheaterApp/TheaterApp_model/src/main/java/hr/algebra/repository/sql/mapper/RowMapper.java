package hr.algebra.repository.sql.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Maps the current row of a {@link ResultSet} to an object of type {@code T}.
 * Implementations must not advance the cursor.
 */
@FunctionalInterface
public interface RowMapper<T> {
    T map(ResultSet rs) throws SQLException;
}
