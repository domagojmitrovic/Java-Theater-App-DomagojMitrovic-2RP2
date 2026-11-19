package hr.algebra.repository.sql.queries;

public final class CitySqlQueries {
    private CitySqlQueries() {
    }

    public static final String SQL_FIND_BY_NAME =
            "SELECT id, name, postal_code FROM city WHERE name ILIKE ? ORDER BY name, postal_code";

    public static final String SQL_INSERT = "INSERT INTO city(name, postal_code) VALUES (?,?)";

    public static final String SQL_FIND_BY_ID = "SELECT id, name, postal_code FROM city WHERE id=?";

    public static final String SQL_FIND_ALL = "SELECT id, name, postal_code FROM city";

    public static final String SQL_UPDATE = "UPDATE city SET name = ?, postal_code = ? WHERE id = ?";

    public static final String SQL_DELETE = """
        DELETE FROM city
        WHERE id = ?
        """;
}
