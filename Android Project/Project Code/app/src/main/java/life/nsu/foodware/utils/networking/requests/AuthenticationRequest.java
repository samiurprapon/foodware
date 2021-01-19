package life.nsu.foodware.utils.networking.requests;

public class AuthenticationRequest {

    private String email;
    private String password;

    public AuthenticationRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
