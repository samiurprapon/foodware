package life.nsu.foodware.utils.networking.responses;

public class ValidationResponse {
    private boolean isCompleted;
    private String message;

    public ValidationResponse(boolean isCompleted, String message) {
        this.isCompleted = isCompleted;
        this.message = message;
    }

    public boolean isCompleted() {
        return isCompleted;
    }
}
