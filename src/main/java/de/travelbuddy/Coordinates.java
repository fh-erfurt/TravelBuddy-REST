package de.travelbuddy;

public class Coordinates {

    private double latitude;
    private double longitude;

    public Coordinates(double latitude, double longitude) {
        if (checkLatitude(latitude))
            //Todo throw ...;

        if (checkLongitude(longitude))
            //Todo throw...;

        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {return latitude;}

    public void setLatitude(double latitude) {this.latitude = latitude;}

    public double getLongitude() {return longitude;}

    public void setLongitude(double longitude) {this.longitude = longitude;}

    private boolean checkLongitude(double longitude) {
        // TODO Übergebenen Wert prüfen, wenn er im zulässigen Bereich liegt true zurück geben, wenn nicht dann false zurückgeben.
        return true;
    }

    private boolean checkLatitude(double latitude) {
        // TODO Übergebenen Wert prüfen, wenn er im zulässigen Bereich liegt true zurück geben, wenn nicht dann false zurückgeben.
        
        return true;
    }
}
