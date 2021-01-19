package life.nsu.foodware.utils.networking.responses;

public class RefreshRequest {
    private String refreshToken;

    public RefreshRequest(String refreshToken) {
        this.refreshToken = "Bearer "+refreshToken;
    }
}
