package life.nsu.foodware.models;


public class User {

    private String email;
    private String password;
    private String type;

    public User() {
        // empty constructor for firebase
    }

    public User(String email, String password, String type) {
        this.email = email;
        this.password = password;
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
