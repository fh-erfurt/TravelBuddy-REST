package de.travelbuddy;

public class Coordinates {

    private double latitude;
    private double longitude;

    // TODO JAVADOC
    public Coordinates(double latitude, double longitude) {
        if (!checkLatitude(latitude)){
            //TODO throw exception
             }

        if (!checkLongitude(longitude)) {
            //TODO throw exception
        }

        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {return latitude;}

    public void setLatitude(double latitude) {this.latitude = latitude;}

    public double getLongitude() {return longitude;}

    public void setLongitude(double longitude) {this.longitude = longitude;}

    // TODO JAVADOC
    boolean checkLatitude(double latitude) {
        return latitude >= -90.0 && latitude <= 90.0;
    }
    // TODO JAVADOC
    // TODO Warum wolltest du das static machen? @Marcel
    boolean checkLongitude(double longitude) {
        return longitude >= -180.0 && longitude <= 180.0;
    }


}
