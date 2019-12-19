package core.models;

import core.enums.Roles;

public class User {
    protected String password;
    protected String login;
    public Roles role;

    @Override
    public String toString() {
        return "Login: " + this.login;
    }
}
