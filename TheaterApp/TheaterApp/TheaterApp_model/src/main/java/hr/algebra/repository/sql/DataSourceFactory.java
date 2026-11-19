package hr.algebra.repository.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import hr.algebra.utilities.ConfigLoader;

import javax.sql.DataSource;

public enum DataSourceFactory {
    INSTANCE;

    private final DataSource dataSource;

    DataSourceFactory() {
        this.dataSource = createDataSource(new ConfigLoader());
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    private DataSource createDataSource(ConfigLoader config) {
        HikariConfig hikari = new HikariConfig();
        hikari.setJdbcUrl(String.format("jdbc:postgresql://%s:%s/%s",
                config.get("PG_HOST"),
                config.get("PG_PORT"),
                config.get("PG_DATABASE")));
        hikari.setUsername(config.get("PG_USER"));
        hikari.setPassword(config.get("PG_PASSWORD"));

        hikari.setMaximumPoolSize(config.getInt("POOL_MAXIMUM_POOL_SIZE"));
        hikari.setMinimumIdle(config.getInt("POOL_MINIMUM_IDLE"));
        hikari.setConnectionTimeout(config.getInt("POOL_CONNECTION_TIMEOUT"));
        hikari.setIdleTimeout(config.getInt("POOL_IDLE_TIMEOUT"));
        hikari.setMaxLifetime(config.getInt("POOL_MAX_LIFETIME"));
        return new HikariDataSource(hikari);
    }
}
