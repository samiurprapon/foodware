package life.nsu.foodware.models;

public class Order {

    private String orderId;
    private String status;
    private String location; // user location;

    public String getOrderId() {
        return orderId;
    }

    public String getStatus() {
        return status;
    }

    public String getLocation() {
        return location;
    }
}
