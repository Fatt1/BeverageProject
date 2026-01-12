package com.fat.DAO.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbContext {
    private static final String URL = "jdbc:sqlserver://localhost:1434;"
            + "database=beverageDB;"
            +"user=sa;"
            +"password=YourStrong!Passw0rd;"
            +"encrypt=true;"
            +"trustServerCertificate=true;"
            +"loginTimeout=30;";



    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
