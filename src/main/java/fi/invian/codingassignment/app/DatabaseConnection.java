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
//            config.setJdbcUrl("jdbc:mariadb://codingassignment-db/messaging"); //Laita kun valmis, helpompi devata tottuneeseen tylliin
            config.setJdbcUrl("jdbc:mysql://localhost:3306/messaging?createDatabaseIfNotExist=true"); //Poista kun valmis, helpompi devata tottuneeseen tylliin
            
            config.setUsername("root"); //Poista kun valmis, helpompi devata tottuneeseen tylliin
//            config.setPassword("root_password"); //Laita kun valmis, helpompi devata tottuneeseen tylliin
            config.setPassword("root");
            config.setPoolName("pool-1");
            config.setMaximumPoolSize(1);//Default recomended is 10, can be usefull?
            
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
