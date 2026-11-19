package hr.algebra.repository.sql.queries;

public final class ActorSqlQueries {
    private ActorSqlQueries() {
    }

    public static final String SQL_SELECT_BASE = """
        SELECT id, first_name, last_name, oib, actor_id
        FROM actor
        """;

    public static final String SQL_FIND_ALL = SQL_SELECT_BASE + """
        ORDER BY last_name, first_name, actor_id
        """;

    public static final String SQL_FIND_BY_NAME = SQL_SELECT_BASE + """
        WHERE first_name ILIKE ?
           OR last_name ILIKE ?
           OR first_name || ' ' || last_name ILIKE ?
        ORDER BY last_name, first_name, actor_id
        """;

    public static final String SQL_FIND_BY_ID = SQL_SELECT_BASE + """
        WHERE id = ?
        """;

    public static final String SQL_INSERT = """
        INSERT INTO actor (first_name, last_name, oib, actor_id)
        VALUES (?, ?, ?, ?)
        RETURNING id
        """;

    public static final String SQL_UPDATE = """
        UPDATE actor
        SET first_name = ?,
            last_name = ?,
            oib = ?,
            actor_id = ?
        WHERE id = ?
        """;

    public static final String SQL_DELETE = """
        DELETE FROM actor
        WHERE id = ?
        """;
}
