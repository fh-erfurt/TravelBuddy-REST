package de.travelbuddy;

public class Adress {

    private static int IDCounter;

    private int adressID;
    private String town;
    private String street;
    private int streetNumber;
    private String ZIP;
    private String country;

    public Adress(int adressID, String town, String street, int streetNumber, String ZIP, String country) {
        IDCounter++;
        this.adressID = IDCounter;
        this.town = town;
        this.street = street;
        this.streetNumber = streetNumber;
        this.ZIP = ZIP;
        this.country = country;
    }

    public static int getIDCounter() {
        return IDCounter;
    }

    public int getAdressID() {
        return adressID;
    }

    public void setAdressID(int adressID) {
        this.adressID = adressID;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getZIP() {
        return ZIP;
    }

    public void setZIP(String ZIP) {
        this.ZIP = ZIP;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
