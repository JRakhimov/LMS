package core.models;

public class SignedInUser {
    private static Object user;
    private static SignedInUser instance;
    private SignedInUser() {

    }
    public static SignedInUser getInstance() {
        return instance;
    }

    public static Object getUser() {
        return user;
    }

    public static void setUser(Object user) {
        SignedInUser.user = user;
    }
}
