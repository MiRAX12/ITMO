package database;

import Network.User;
import model.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class WorkerService {
    private static Connection connection;

    public WorkerService(Connection connection) {
        WorkerService.connection = connection;
    }

    public static boolean addWorker(Worker worker, User user) throws SQLException {

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
            if (worker.getEndDate() != null) {
                p.setString(7, worker.getEndDate().toString());
            } else p.setNull(7, Types.VARCHAR);
            p.setString(8, worker.getStatus().toString());
            if (worker.getPerson() != null) {
                if (worker.getPerson().getPassportId() != null) {
                    p.setString(9, worker.getPerson().getPassportId());
                } else p.setNull(9, Types.VARCHAR);
                p.setFloat(10, worker.getPerson().getLocation().getX());
                p.setFloat(11, worker.getPerson().getLocation().getY());
                p.setLong(12, worker.getPerson().getLocation().getZ());
            } else {
                p.setNull(9, Types.VARCHAR);
                p.setNull(10, Types.FLOAT);
                p.setNull(11, Types.FLOAT);
                p.setNull(12, Types.BIGINT);
            }
            p.setString(13, user.getUsername());
            p.executeUpdate();
            connection.commit();
            return true;

        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback(); // Откатываем при ошибке
            }
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static Map<Long, Worker> loadWorkers() {
        Map<Long, Worker> workerMap = new LinkedHashMap<>();
        String query = "SELECT * FROM workers";

        try (Statement stat = connection.createStatement();
             ResultSet res = stat.executeQuery(query)) {
            while (res.next()) {
                Long id = res.getLong("id");
                String name = res.getString("name");
                float x = res.getFloat("coordinate_x");
                long y = res.getLong("coordinate_y");

                Coordinates coordinates = new Coordinates();
                coordinates.setX(x);
                coordinates.setY(y);

                LocalDate creationDate = LocalDate.parse(res.getString("creation_date"));
                float salary = res.getFloat("salary");
                LocalDateTime startDate = LocalDateTime.parse(res.getString("start_date"));

                ZonedDateTime endDate;
                if (res.getString("end_date") != null) {
                    endDate = ZonedDateTime.parse(res.getString("end_date"));
                } else endDate = null;

                Status status = Status.valueOf(res.getString("status"));

                Person person;
                if (res.getString("location_x") != null) {
                    person = new Person();
                    String passportId;
                    if (res.getString("passport_id") != null) {
                        passportId = res.getString("passport_id");
                    } else passportId = null;
                    var location_x = res.getFloat("location_x");
                    var location_y = res.getFloat("location_y");
                    var location_z = res.getLong("location_z");
                    Location location = new Location.Builder().x(location_x).y(location_y).z(location_z).build();
                    person.setPassportId(passportId);
                    person.setLocation(location);
                } else {
                    person = null;
                }

                Worker worker = new Worker.Builder().id(id).name(name).coordinates(coordinates)
                        .creationDate(creationDate).salary(salary).startDate(startDate).endDate(endDate)
                        .status(status).person(person).build();
                workerMap.put(id, worker);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return workerMap;
    }

    public static Long getLastUserInsert(User user) {
        String query = "SELECT * FROM workers WHERE creator_id = (SELECT id FROM users WHERE username = ?)" +
                " ORDER BY id DESC LIMIT 1";
        try (PreparedStatement p = connection.prepareStatement(query)) {
            p.setString(1, user.getUsername());
            try (ResultSet rs = p.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("id");  // Возвращаем ID последнего работника
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
