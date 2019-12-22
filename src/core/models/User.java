package core.models;

public class User {
    protected int id;
    protected String login;
    protected String password;


    public User(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    @Override
    public String toString() { return "Login: " + this.login; }
}
