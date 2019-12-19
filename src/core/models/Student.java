package core.models;

import core.enums.Course;
import core.enums.Department;
import core.enums.Roles;

import java.time.LocalDate;
import java.time.ZoneId;

public class Student extends User {
    private Course course;
    private Department department;

    private Book[] books;
    private Book[] reservedBooks;

    private double fine = 0;
    private boolean isBlocked = false;
    private Roles role = Roles.STUDENT;

    public Student(String login, String password) {
        this.login = login;
        this.password = password;

        // 0 if they are equal
        if (Character.compare(login.charAt(5), '1') == 0) {
            this.department = Department.SOL;
        } else {
            this.department = Department.SOCIE;
        }

        this.course = Student.getCourse(login);
    }

    public static Course getCourse(String login) {
        int studentYear = Integer.parseInt("20" + login.substring(1, 2));

        LocalDate localDate = LocalDate.now(ZoneId.systemDefault());
        int currentMonth = localDate.getMonth().getValue();
        int currentYear = localDate.getYear();

        int difference = currentYear - studentYear;

        if (currentMonth <= 6) {
            difference = difference - 1;
        }

        switch (difference) {
            case 0: return Course.FRESHMAN;
            case 1: return Course.SOPHOMORE;
            case 2: return Course.JUNIOR;
            case 3: return Course.SENIOR;
        }

        return Course.NOT_FOUND;
    }

    @Override
    public String toString() {
        return "Login: " + this.login;
    }
}
