package life.nsu.foodware.utils.networking.requests;

public class RegistrationRequest {

    private String email;
    private String password;
    private String type;

    public RegistrationRequest(String email, String password, String type) {
        this.email = email;
        this.password = password;
        this.type = type;
    }
}
