package life.nsu.foodware.models;

public class Location {
    double longitude;
    double latitude;
    String timestamps;

    public Location() {
        // empty constructor for firebase
    }

    public Location(double longitude, double latitude, String timestamps) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.timestamps = timestamps;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(String timestamps) {
        this.timestamps = timestamps;
    }

    @Override
    public String toString() {
        return "Location{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                ", timestamps='" + timestamps + '\'' +
                '}';
    }
}
