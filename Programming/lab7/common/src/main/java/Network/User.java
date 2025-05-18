package Network;

import java.io.Serializable;

public class User implements Serializable {
    private final String name;
    private final String password;
    private final String salt;

    public User(String name, String password, String salt) {
        this.name = name;
        this.password = password;
        this.salt = salt;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
