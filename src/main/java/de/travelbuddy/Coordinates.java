package de.travelbuddy;

public class Coordinates {

    private static int IDCounter;

    private int coordinateID;
    private String latitude;
    private String longitute;

    public Coordinates(int coordinateID, String latitude, String longitute) {

        IDCounter++;
        this.coordinateID = IDCounter;
        this.latitude = latitude;
        this.longitute = longitute;
    }

    public static int getIDCounter() {
        return IDCounter;
    }

    public static void setIDCounter(int IDCounter) {
        Coordinates.IDCounter = IDCounter;
    }

    public int getCoordinateID() {
        return coordinateID;
    }

    public void setCoordinateID(int coordinateID) {
        this.coordinateID = coordinateID;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitute() {
        return longitute;
    }

    public void setLongitute(String longitute) {
        this.longitute = longitute;
    }
}
