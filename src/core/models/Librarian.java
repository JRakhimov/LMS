package core.models;

import core.enums.Roles;

public class Librarian {
    private String login;
    private String password;

    private Book[] addedBooks[];

    Roles role = Roles.LIBRARIAN;
}
