package database;

import java.sql.Connection;

public class UserService {
    private static Connection connection;

    public UserService(Connection connection) {
        UserService.connection = connection;
    }


}
