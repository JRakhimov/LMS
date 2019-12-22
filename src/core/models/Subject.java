package core.models;

public class Subject {
    int id;
    String title;

    public Subject(int id, String title) {
        this.id = id;
        this.title = title;
    }

    @Override
    public String toString() { return "title: " + this.title; }
}
