package life.nsu.foodware.utils.networking.responses;

public class AuthenticationResponse {
    private String message;
    private String type;
    private String accessToken;
    private String refreshToken;

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
