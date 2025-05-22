package Network;

import java.io.Serializable;

public class User implements Serializable {
    private String userName;
    private String password;
    private final String salt;

    public User(String userName, String password, String salt) {
        this.userName = userName;
        this.password = password;
        this.salt = salt;
    }

    public User(String userName, String password) {
        this(userName, password, null);
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
