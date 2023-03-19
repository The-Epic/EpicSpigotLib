package me.epic.spigotlib.storage;

import com.zaxxer.hikari.HikariDataSource;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

public class MySQLConnectionPool {

    private HikariDataSource dataSource = null;

    public MySQLConnectionPool(String name, String databaseName, String ip, String port, String username, String password) {
        setupConnection(name, databaseName, ip, port, username, password);
    }

    private void setupConnection(String name, String databaseName, String ip, String port, String username, String password) {
        String url = "jdbc:mysql://" + ip + ":" + port + "/" + databaseName;

        this.dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setMaximumPoolSize(15);
        dataSource.setPoolName(name + "-Connection-Pool");

        dataSource.setUsername(username);
        dataSource.setPassword(password);

        dataSource.addDataSourceProperty("useUnicode", "true");
        dataSource.addDataSourceProperty("characterEncoding", "utf-8");
        dataSource.addDataSourceProperty("rewriteBatchedStatements", "true");
        dataSource.addDataSourceProperty("tcpKeepAlive", true);

        dataSource.addDataSourceProperty("cachePrepStmts", "true");
        dataSource.addDataSourceProperty("prepStmtCacheSize", "250");
        dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        dataSource.addDataSourceProperty("useServerPrepStmts", "true");

        dataSource.addDataSourceProperty("useSSL", false);
        dataSource.addDataSourceProperty("verifyServerCertificate", "false");
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = this.dataSource.getConnection();
        } catch (SQLException ex) {
            // PANIC
            ex.printStackTrace();
        }
        return connection;
    }

    public void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
