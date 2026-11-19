package hr.algebra.repository.sql;

import hr.algebra.model.DirectionStyle;
import hr.algebra.model.Director;
import hr.algebra.repository.DirectorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.algebra.repository.sql.mapper.DirectorRowMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;

import static hr.algebra.repository.sql.queries.DirectorSqlQueries.*;

public class DirectorSqlRepository extends BaseSqlRepository<Director> implements DirectorRepository {
    public DirectorSqlRepository(DataSource dataSource) {
        super(dataSource, DirectorRowMapper.INSTANCE);
    }
    private static final Logger log= LoggerFactory.getLogger(DirectorSqlRepository.class);

    @Override
    protected void mapEntityToTable(PreparedStatement ps, Director director) throws SQLException {

        ps.setString(1, director.getFirstName());
        ps.setString(2, director.getLastName());
        ps.setString(3, director.getOib());
        ps.setString(4, director.getDirectorId());

        if (director.getDirectionStyle() == null) {
            ps.setNull(5, Types.OTHER);
        } else {
            ps.setString(5, director.getDirectionStyle().name());
        }

    }

    @Override
    public Director create(Director director) throws SQLException {
        long id = executeInsertReturningId(
                SQL_INSERT,
                ps -> mapEntityToTable(ps, director)
        );

        return director.toBuilder().id(id).build();
    }

    @Override
    public Optional<Director> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }

        try {
            return findSingleResult(
                    SQL_FIND_BY_ID, ps -> ps.setLong(1, id));

        } catch (SQLException e) {
            log.error("Failed to find director with ID={}.", id, e);
            return Optional.empty();
        }
    }

    @Override
    public List<Director> findByName(String name) {
        if (name == null || name.isBlank()) {
            return List.of();
        }

        try {
            String searchTerm = "%" + name.trim() + "%";

            return findMany(
                    SQL_FIND_BY_NAME,
                    ps -> {
                        ps.setString(1, searchTerm);
                        ps.setString(2, searchTerm);
                        ps.setString(3, searchTerm);
                    }
            );

        } catch (SQLException e) {
            log.error("Failed to find directors matching name '{}'.", name, e);
            return List.of();
        }
    }

    @Override
    public List<Director> retrieveAll() {
        try {
            return findMany(SQL_FIND_ALL, ps -> {});

        } catch (SQLException e) {
            log.error("Failed to retrieve all directors.", e);
            return List.of();
        }
    }

    @Override
    public int update(Director director) {
        if (director == null || director.getId() == null) {
            return 0;
        }

        try {
            return executeUpdate(
                    SQL_UPDATE,
                    ps -> {
                        mapEntityToTable(ps, director);
                        ps.setLong(6, director.getId());
                    }
            );

        } catch (SQLException e) {
            log.error(
                    "Failed to update director with ID={}.",
                    director.getId(),
                    e
            );
            return 0;
        }
    }

    @Override
    public int delete(Long id) {
        if (id == null) {
            return 0;
        }

        try {
            return executeUpdate(SQL_DELETE,
                    ps -> ps.setLong(1, id));

        } catch (SQLException e) {
            log.error("Failed to delete director with ID={}.", id, e);
            return 0;
        }
    }

    private String generateNextDirectorId() {
        int highestNumber = retrieveAll()
                .stream()
                .map(Director::getDirectorId)
                .mapToInt(directorId ->
                        Integer.parseInt(directorId.substring(4))
                )
                .max()
                .orElse(0);

        return String.format("DIR-%03d", highestNumber + 1);
    }
}
