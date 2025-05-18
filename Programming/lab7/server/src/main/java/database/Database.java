package database;

import Network.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    private static Connection connection;

    public static void establishConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            Properties info = new Properties();
            info.load(new FileInputStream("db.cfg"));
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/studs", info);
            System.out.println(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addUser(User user) {
        var username = user.getName();
        var hashedPassword = user.getPassword();

        String query = "INSERT INTO users (username, hashedpassword, ) VALUES (?, ?)";

        try (PreparedStatement p = connection.prepareStatement(query)) {

            p.setString(1, username);
            p.setString(2, hashedPassword);
            p.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
