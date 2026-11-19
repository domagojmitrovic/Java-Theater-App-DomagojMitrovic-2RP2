package hr.algebra.repository.sql;

import hr.algebra.model.City;
import hr.algebra.repository.CityRepository;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

import org.slf4j.Logger;

import hr.algebra.repository.sql.mapper.CityRowMapper;

import javax.sql.DataSource;

import static hr.algebra.repository.sql.queries.CitySqlQueries.*;

public class CitySqlRepository extends BaseSqlRepository<City> implements CityRepository {

private static final Logger log = LoggerFactory.getLogger(CitySqlRepository.class);

    public CitySqlRepository(DataSource dataSource) {
        super(dataSource, CityRowMapper.INSTANCE);
    }

    @Override
    public List<City> findByName(String name) {
        try {
            return findMany(
                    SQL_FIND_BY_NAME,
                    ps -> ps.setString(1, "%" + name + "%")
            );
        } catch (SQLException e) {
            log.error("Greška pri pretraživanju gradova po nazivu {}.", name, e);
            return List.of();
        }
    }

    @Override
    public City create(City city) throws SQLException {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) // tells the driver to return the generated key
        {
            mapEntityToTable(ps, city);
        int affectedRows=ps.executeUpdate();
        if(affectedRows==0){
            throw new SQLException("Insert failed");
        }
        try(ResultSet generatedKey=ps.getGeneratedKeys()){
            if(generatedKey.next()) {
                Long generatedId = generatedKey.getLong(1);
                City cityWithId = city.toBuilder().id(generatedId).build();
                log.info("New city added ID={}", generatedId);
                return cityWithId;
            }

        }
    }
         catch (SQLException e) {
            log.info("Error saving City {}:{}",city,e.getMessage(),e);
        }
        return city;
    }

    @Override
    public Optional<City> findById(Long id) {
        try {
            return findSingleResult(
                    SQL_FIND_BY_ID,
                    ps -> ps.setLong(1, id)
            );
        } catch (SQLException e) {
            log.error("Error retriving city  ID {}.", id, e);
            return Optional.empty();
        }
    }

    @Override
    public List<City> retrieveAll() {
        try {
            List<City> cities = findMany(SQL_FIND_ALL, ps -> {});
            log.info("findAll()-> found {} cities", cities.size());
            return cities;
        } catch (SQLException e) {
            log.error("Error retrieving cities {}", e.getMessage(), e);
            return List.of();
        }
    }

    @Override
    public int update(City city) {
        try {
            return executeUpdate(SQL_UPDATE, ps -> {
                ps.setString(1, city.name());
                ps.setString(2, city.postalCode());
                ps.setLong(3, city.id());
            });
        } catch (SQLException e) {
            log.error("Error updating city {}.", city.name(), e);
            return 0;
        }
    }

    @Override
    public int delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("City ID can't be null");
        }

        try {
            int deleted = executeUpdate(
                    SQL_DELETE,
                    ps -> ps.setLong(1, id)
            );

            log.info("Deleted {} cities for ID={}.", deleted, id);
            return deleted;

        } catch (SQLException e) {
            log.error("Error deleting city with ID={}.", id, e);
            return 0;
        }
    }

    @Override
    protected void mapEntityToTable
            (PreparedStatement ps, City city) throws SQLException {
        ps.setString(1, city.name());
        ps.setString(2, city.postalCode());
    }
}
