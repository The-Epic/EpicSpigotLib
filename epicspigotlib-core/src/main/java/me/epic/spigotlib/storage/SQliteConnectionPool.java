package me.epic.spigotlib.storage;

import com.zaxxer.hikari.HikariDataSource;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

public class SQliteConnectionPool {
    private HikariDataSource dataSource = null;

    public SQliteConnectionPool(String name, String fileName, File dataDir) {
        setupConnection(name, fileName, dataDir);
    }

    private void setupConnection(String name, String fileName, File dataDir) {
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }

        String url = "jdbc:sqlite:" + dataDir.getAbsolutePath() + "/" + fileName + ".db";

        this.dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setMaximumPoolSize(15);
        dataSource.setPoolName(name + "-Connection-Pool");

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
        } catch (SQLException e) {
            // PANIC
            e.printStackTrace();
        }
        return connection;
    }

    public void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
