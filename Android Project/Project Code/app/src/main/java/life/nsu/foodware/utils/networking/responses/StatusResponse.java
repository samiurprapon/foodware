package life.nsu.foodware.utils.networking.responses;

public class StatusResponse {
    private boolean isCompleted;
    private String message;

    public StatusResponse(boolean isCompleted, String message) {
        this.isCompleted = isCompleted;
        this.message = message;
    }

    public boolean isCompleted() {
        return isCompleted;
    }
}
