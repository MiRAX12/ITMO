package database;

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

        String query = "INSERT INTO users (username, hashedpassword, ) VALUES (?, ?)";

        try (PreparedStatement p = connection.prepareStatement(query)) {

            p.setString(1, username);
            p.setString(2, hashedPassword);
            p.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean addWorker(Worker worker){

        String query = "INSERT INTO workers (name, coordinate_x, coordinate_y,\n" +
                "creation_date, salary, start_date, end_date, status, passport_id, location_x,\n" +
                "location_y, location_z, creator_id)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?, (SELECT id FROM users WHERE username = ?))";

        try (PreparedStatement p = connection.prepareStatement(query)){
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

            p.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
