package Network;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String password;
    private final String salt;

    public User(String name, String password, String salt) {
        this.name = name;
        this.password = password;
        this.salt = salt;
    }

    public User(String name, String password) {
        this(name, password, null);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
