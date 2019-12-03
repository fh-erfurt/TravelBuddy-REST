package de.travelbuddy;

public class Coordinates {

    private String latitude;
    private String longitute;

    public Coordinates(String latitude, String longitute) {
        this.latitude = latitude;
        this.longitute = longitute;
    }

    public String getLatitude() {return latitude;}

    public void setLatitude(String latitude) {this.latitude = latitude;}

    public String getLongitute() {return longitute;}

    public void setLongitute(String longitute) {this.longitute = longitute;}
}
