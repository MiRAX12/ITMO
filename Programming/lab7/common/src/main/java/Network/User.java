package Network;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;

    public void setSalt(String salt) {
        this.salt = salt;
    }

    private String salt;
    private String status;

    public User(String userName, String password, String salt) {
        this.username = userName;
        this.password = password;
        this.salt = salt;
    }

    public User(String userName, String password) {
        this(userName, password, null);
    }

    public User() {
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSalt() {
        return salt;
    }
}
