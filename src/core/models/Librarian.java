package core.models;

import core.enums.Roles;

public class Librarian extends User {
    private Book[] addedBooks[];

    public Librarian(int id, String login, String password) {
        super(id, login, password);
    }

    Roles role = Roles.LIBRARIAN;
}
