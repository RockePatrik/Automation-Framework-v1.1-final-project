package com.store.utils;

import java.sql.*;

public class DBValidation {

    private static final String URL_CONNECTION = "jdbc:mysql://localhost:3306/selenium";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL_CONNECTION, USER, PASSWORD);
    }

    public String getUserByEmail(String email) {
        String query = "SELECT * FROM customers WHERE email = ?";
        String userData = "";

        try (Connection connection = getConnection();

             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email); // el índice en JDBC empieza en 1

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    userData = resultSet.getString("firstname");
                } else {
                    userData = "registro incompleto";
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al ejecutar consulta: " + e.getMessage());
            userData = "error al conectar con BD";
        }
        return userData;
    }
}
