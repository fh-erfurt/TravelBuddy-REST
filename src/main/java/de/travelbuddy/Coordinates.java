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

    static boolean checkLongitude(double longitude) {
        if(longitude >=-180.0 && longitude <= 180.0){
            return true;
        }else{
            return false;
        }
        // TODO Übergebenen Wert prüfen, wenn er im zulässigen Bereich liegt true zurück geben, wenn nicht dann false zurückgeben.

    }

    static boolean checkLatitude(double latitude) {
        if(latitude >=-90.0 && latitude <= 90.0){
            return true;
        }else{
            return false;
        }
        // TODO Übergebenen Wert prüfen, wenn er im zulässigen Bereich liegt true zurück geben, wenn nicht dann false zurückgeben.
        

    }

}
