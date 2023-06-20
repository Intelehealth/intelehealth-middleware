/**
 * 
 */
package org.intelehealth.middleware.utils;
import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


public class HikariCPDataSource {
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;
    
    static {
    	config.setDriverClassName("com.mysql.cj.jdbc.Driver");
    	config.setJdbcUrl("jdbc:mysql://localhost:3306/openmrs?autoreconnect=true");
        config.setUsername("root");
        config.setPassword("i10hi1c");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        
        config.setMaximumPoolSize(50);  
        config.setMinimumIdle(15);  
        config.setMaxLifetime(20);  
        config.setIdleTimeout(10);   
        
        config.setConnectionTestQuery("SELECT 1");
        ds = new HikariDataSource(config);
    }
    
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
     
    public static void closeConnection() throws SQLException {
        ds.close();     
    }  
    
    private HikariCPDataSource(){}
    
}