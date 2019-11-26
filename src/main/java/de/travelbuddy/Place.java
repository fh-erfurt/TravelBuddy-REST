package de.travelbuddy;

import java.security.ProtectionDomain;
import java.time.LocalDateTime;


//Verständnisfrage: hier unbedingt protected erforderlich? Oder kann die initialisierung in Tochterklassen auch über super konstruktor erfolgen?
public abstract class Place {

    private static int IDCounter;

    protected int PlaceID;
    protected String name;
    protected Coordinates coordiantes;
    protected Adress adress;
    protected LocalDateTime arrive;
    protected LocalDateTime departure;
    protected Connection connectionToNextDestination;
    protected Expense expense;

    public Place(int placeID, String name, Coordinates coordiantes, Adress adress, LocalDateTime arrive, LocalDateTime departure, Connection connectionToNextDestination, Expense expense) {
        IDCounter++;
        this.PlaceID = IDCounter;
        this.name = name;
        this.coordiantes = coordiantes;
        this.adress = adress;
        this.arrive = arrive;
        this.departure = departure;
        this.connectionToNextDestination = connectionToNextDestination;
        this.expense = expense;
    }

    public static int getIDCounter() {
        return IDCounter;
    }

    public int getPlaceID() {
        return PlaceID;
    }

    public void setPlaceID(int placeID) {
        PlaceID = placeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordiantes() {
        return coordiantes;
    }

    public void setCoordiantes(Coordinates coordiantes) {
        this.coordiantes = coordiantes;
    }

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }

    public LocalDateTime getArrive() {
        return arrive;
    }

    public void setArrive(LocalDateTime arrive) {
        this.arrive = arrive;
    }

    public LocalDateTime getDeparture() {
        return departure;
    }

    public void setDeparture(LocalDateTime departure) {
        this.departure = departure;
    }

    public Connection getConnectionToNextDestination() {
        return connectionToNextDestination;
    }

    public void setConnectionToNextDestination(Connection connectionToNextDestination) {
        this.connectionToNextDestination = connectionToNextDestination;
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }
}
