package core.models;

import core.enums.Roles;

public class Administrator {
    private String login;
    private String password;

    private Book[] addedBooks[];

    Roles role = Roles.ADMINISTRATOR;
}
