package core.models;

import core.enums.Roles;

public class Administrator extends User {
    private Book[] addedBooks[];
    Roles role = Roles.ADMINISTRATOR;

    public Administrator(int id, String login, String password) {
        super(id, login, password);
    }
}
