package database;

import Network.PasswordHasher;
import Network.User;
import model.Worker;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
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
        var salt = PasswordHasher.generateSalt();
        String query = "INSERT INTO users (username, hashedpassword, salt) VALUES (?, ?, ?)";

        try (PreparedStatement p = connection.prepareStatement(query)) {

            p.setString(1, username);
            p.setString(2, hashedPassword);
            p.setString(3, salt);
            p.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean addWorker(Worker worker, User user) {

        String query = "INSERT INTO workers (name, coordinate_x, coordinate_y,\n" +
                "creation_date, salary, start_date, end_date, status, passport_id, location_x,\n" +
                "location_y, location_z, creator_id)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?, (SELECT id FROM users WHERE username = ?))";

        try (PreparedStatement p = connection.prepareStatement(query)) {
            p.setString(1, worker.getName());
            p.setFloat(2, worker.getCoordinates().getX());
            p.setLong(3, worker.getCoordinates().getY());
            p.setString(4, worker.getCreationDate().toString());
            p.setFloat(5, worker.getSalary());
            p.setString(6, worker.getStartDate().toString());
            p.setString(7, worker.getEndDate().toString());
            p.setString(8, worker.getStatus().toString());
            p.setString(9, worker.getPerson().getPassportId());
            p.setFloat(10, worker.getPerson().getLocation().getX());
            p.setFloat(11, worker.getPerson().getLocation().getY());
            p.setLong(12, worker.getPerson().getLocation().getZ());
            p.setString(13, user.getName());
            p.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkUserExistence(String username) {
        String query = "SELECT EXISTS(SELECT 1 FROM users WHERE username = ?)";
        try (PreparedStatement p = connection.prepareStatement(query)) {
            p.setString(1, username);
            ResultSet res = p.executeQuery();
            if (res.next()) {
                return res.getBoolean(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public static boolean checkUserPassword(User user) {
        var username = user.getName();
        String query = "SELECT hashedpassword, salt FROM users WHERE username = ?";
        try (PreparedStatement p = connection.prepareStatement(query)) {
            p.setString(1, username);
            ResultSet res = p.executeQuery();
            if (res.next()) {
                String storedHashedPassword = res.getString("hashedpassword");
                String salt = res.getString("salt");
                var hashedPassword = PasswordHasher.toSHA1(user.getPassword(), salt);
                return storedHashedPassword.equals(hashedPassword);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

}

