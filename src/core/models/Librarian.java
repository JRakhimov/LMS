package core.models;

import core.enums.Roles;

public class Librarian extends User{
    private Book[] addedBooks[];

    Roles role = Roles.LIBRARIAN;
}
