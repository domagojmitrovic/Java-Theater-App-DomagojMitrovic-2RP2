package hr.algebra.repository.sql.queries;

public final class CountrySqlQueries {
    private CountrySqlQueries() {
    }

    public static final String SQL_FIND_BY_NAME = """
        SELECT id, country_code, state_name
        FROM country
        WHERE state_name ILIKE ?
        ORDER BY state_name
        """;

    public static final String SQL_FIND_BY_COUNTRY_CODE = """
        SELECT id, country_code, state_name
        FROM country
        WHERE country_code = ?
        """;

    public static final String SQL_INSERT = """
        INSERT INTO country (country_code, state_name)
        VALUES (?, ?)
        RETURNING id
        """;

    public static final String SQL_FIND_BY_ID = """
        SELECT id, country_code, state_name
        FROM country
        WHERE id = ?
        """;

    public static final String SQL_FIND_ALL = """
        SELECT id, country_code, state_name
        FROM country
        ORDER BY state_name, country_code
        """;

    public static final String SQL_UPDATE = """
        UPDATE country
        SET country_code = ?,
            state_name = ?
        WHERE id = ?
        """;

    public static final String SQL_DELETE = """
        DELETE FROM country
        WHERE id = ?
        """;
}
