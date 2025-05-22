package Network;

public class UserBuilder {
    private String userName;
    private String password;
    private String salt = null;

    public UserBuilder setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder setSalt(String salt) {
        this.salt = salt;
        return this;
    }

    public User createUser() {
        return new User(userName, password, salt);
    }
}