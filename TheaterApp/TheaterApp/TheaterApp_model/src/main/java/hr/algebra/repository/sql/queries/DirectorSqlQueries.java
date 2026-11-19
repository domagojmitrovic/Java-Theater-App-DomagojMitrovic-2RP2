package hr.algebra.repository.sql.queries;

public final class DirectorSqlQueries {
    private DirectorSqlQueries() {
    }

    public static final String SQL_INSERT = """
        INSERT INTO director (
            first_name,
            last_name,
            oib,
            director_id,
            direction_style
        )
        VALUES (?, ?, ?, ?, ?::direction_style)
        RETURNING id
        """;

    public static final String SQL_FIND_BY_ID = """
        SELECT id, first_name, last_name, oib, director_id, direction_style
        FROM director
        WHERE id = ?
        """;

    public static final String SQL_FIND_BY_NAME = """
        SELECT id, first_name, last_name, oib, director_id, direction_style
        FROM director
        WHERE first_name ILIKE ?
           OR last_name ILIKE ?
           OR first_name || ' ' || last_name ILIKE ?
        ORDER BY last_name, first_name
        """;

    public static final String SQL_FIND_ALL = """
        SELECT id, first_name, last_name, oib, director_id, direction_style
        FROM director
        ORDER BY last_name, first_name, director_id
        """;

    public static final String SQL_UPDATE = """
        UPDATE director
        SET first_name = ?,
            last_name = ?,
            oib = ?,
            director_id = ?,
            direction_style = ?::direction_style
        WHERE id = ?
        """;

    public static final String SQL_DELETE = """
        DELETE FROM director
        WHERE id = ?
        """;
}
