package de.travelbuddy;

public class Contact {

    private static int IDCounter;

    private int contactID;
    private String phone;
    private String email;

    public Contact(int contactID, String phone, String email) {
        IDCounter++;
        this.contactID = contactID;
        this.phone = phone;
        this.email = email;
    }

    public static int getIDCounter() {
        return IDCounter;
    }

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
