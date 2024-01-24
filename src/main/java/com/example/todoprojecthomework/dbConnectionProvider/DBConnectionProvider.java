package com.example.todoprojecthomework.dbConnectionProvider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionProvider {

    private final String DB_URL = "jdbc:mysql://localhost:3306/my_to_do";
    private final String USERNAME = "root";
    private final String PASSWORD = "root";

    private static DBConnectionProvider dbConnectionProvider = new DBConnectionProvider();
    private Connection connection;

    private DBConnectionProvider() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static DBConnectionProvider getInstance() {
        return dbConnectionProvider;
    }


    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
