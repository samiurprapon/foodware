package life.nsu.foodware.utils.networking.responses;

import life.nsu.foodware.models.Restaurant;

public class RestaurantResponse {
    private String message;
    private Restaurant restaurant;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
