package fi.invian.codingassignment.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.mariadb.jdbc.Driver;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {
    private static HikariDataSource dataSource;

    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    private static DataSource getDataSource() {
        if (dataSource == null) {
            HikariConfig config = new HikariConfig();
            config.setDriverClassName(Driver.class.getName());
            config.setJdbcUrl("jdbc:mariadb://codingassignment-db/messaging");
            config.setUsername("root");
            config.setPassword("root_password");
            config.setPoolName("pool-1");
            config.setMaximumPoolSize(1);

            config.setLeakDetectionThreshold(5000);
            config.setConnectionTimeout(1000);

            dataSource = new HikariDataSource(config);
        }

        return dataSource;
    }

    static void closeDataSource() {
        dataSource.close();
        dataSource = null;
    }

    private DatabaseConnection() {}
}
