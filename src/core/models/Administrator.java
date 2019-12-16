package core.models;

import core.enums.Roles;

public class Administrator extends User {
    private Book[] addedBooks[];

    Roles role = Roles.ADMINISTRATOR;
}
