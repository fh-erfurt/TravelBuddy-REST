package de.travelbuddy.navigation;

import de.travelbuddy.*;
import de.travelbuddy.place.Accommodation;
import de.travelbuddy.place.Place;

import java.time.LocalDateTime;
import java.util.Scanner;


//TODO Exception für Menü Ausfall
public class Menu {

    public boolean menuJourneyManager() {

        JourneyManager Test = new JourneyManager();

        System.out.println("\t-- Journeymanager --\n\n " +
                "1 - Journey anlegen \n" +
                "2 - Journey auswählen \n" +
                "3 - Journey löschen \n" +
                "0 - Programm Beenden");

        Scanner scMenu = new Scanner(System.in);
        int menu;
        menu = scMenu.nextInt();

        switch (menu) {
            case 1://Journey anlegen
                Test.AddJourney(createJourney());
                //TODO Ausgabe Journey angelegt
                break;

            case 2://Journey auswählen
               /*TODO Test.GetJourney(selectJourney())
                menuJourney(selectedJourney)

                */
                break;
            case 3://Journey löschen
                //TODO Test.RemoveJourney(Test.GetJourney(selectJourney()))

                break;

            case 0://Programm Beenden
                scMenu.close();
                return true;

            default:
                return false;
        }

        return false; //Todo remove
    }

    public boolean menuJourney(Journey selectedJourney) {
        //TODO Journey übersicht anzeigen
        System.out.println("\t-- Journey " + selectedJourney.getTitle() + "--\n\n" +
                "1 - Place anlegen \n" +
                "2 - Place auswählen \n" +
                "3 - Place löschen \n" +
                "4 - Person anlegen \n" +
                "5 - Person auswählen \n" +
                "6 - Person löschen \n" +
                "7 - Journey bearbeiten \n" +
                "0 - Journey verlassen");

        Scanner scMenuJ = new Scanner(System.in);
        int menu;
        menu = scMenuJ.nextInt();

        switch (menu) {
            case 1://Place anlegen
                selectedJourney.addPlace(createPlace());
                //TODO Ausgabe Place angelegt
                break;

            case 2://Place auswählen
               /*TODO selectedJourney.getPlace(selectPlace())
                menuPlace(selectedPlace)

                */
                break;

            case 3://Place löschen
                //TODO selectedJourney.removePlace(selectedJourney.getPlace(selectPlace))

                break;

            case 4://Person anlegen
                //selectedJourney.addPerson(createPerson());
                //TODO Ausgabe Person angelegt
                break;

            case 5://Person auswählen
                /*TODO selectedJourney.getPerson(selectPerson())
                menuPerson(selectedPerson)

                */
                break;

            case 6://Person löschen
                //TODO selectedJourney.removePerson(selectedJourney.getPerson(selectPerson))

                break;

            case 7://Journey bearbeiten
                //TODO alterJourney(selectedJourney);
                break;

            case 0://Journey verlassen
                scMenuJ.close();
                return true;

            default:
                return false;
        }
        return false; //Todo remove
    }

    public boolean menuPlace(Place selectedPlace) {
        //TODO Place übersicht anzeigen
        System.out.println("\t-- Place " + selectedPlace.getName() + "--\n\n" +
                "1 - Expense anlegen \n" +
                "2 - Expense auswählen \n" +
                "3 - Expense löschen \n" +
                "4 - Place bearbeiten \n" +
                "0 - Place verlassen");

        Scanner scMenuPl = new Scanner(System.in);
        int menu;
        menu = scMenuPl.nextInt();

        switch (menu) {
            case 1://Expense anlegen
                //selectedPlace.addExpense(createExpense());
                //TODO Ausgabe Expense angelegt
                break;

            case 2:// Expense auswählen
               /*TODO selectedPlace.getExpense(selectExpense())
                menuJourney(selectedJourney)

                */
                break;

            case 3://Expense löschen
                //TODO selectedPlace.removeExpense(getExpense(selectExpense()));
                break;

            case 4://Place bearbeiten
                //TODO Test.RemoveJourney(Test.GetJourney(selectJourney()))

                break;

            case 0://Place verlassen
                scMenuPl.close();
                return true;

            default:
                return false;
        }

        return false; //Todo remove
    }

    public boolean menuPerson(Person selectedPerson) {
        System.out.println("\t-- Person " + selectedPerson.getFirstName() + " " + selectedPerson.getName() + "--\n\n" +
                "1 - Person bearbeiten \n" +
                "0 - Person verlassen");

        Scanner scMenuPer = new Scanner(System.in);
        int menu;
        menu = scMenuPer.nextInt();

        switch (menu) {
            case 1://Person bearbeiten
                //TODO alterPerson(selectedPerson);
                break;

            case 0://Person verlassen
                scMenuPer.close();
                return true;

            default:
                return false;
        }

        return false; //Todo remmove
    }

    public boolean menuExpense(Expense selectedExpense) {
        System.out.println("\t-- Expense " + selectedExpense.getTitle() + "--\n\n" +
                "1 - Person hinzufügen \n" +
                "2 - Person löschen \n" +
                "3 - Expense bearbeiten \n" +
                "0 - Programm Beenden");

        Scanner scMenuExp = new Scanner(System.in);
        int menu;
        menu = scMenuExp.nextInt();

        switch (menu) {
            case 1://Person hinzufügen
                //TODO selectedExpense.addPerson(getPerson out off Joruney list);
                //TODO Ausgabe Expense angelegt
                break;

            case 2://Person löschen
                //TODO selectedExpense.removePerson(selectPerson());
                break;

            case 3://Expense bearbeiten
                //TODO alterExpense(selectedExpense)
                break;

            case 0://Expense verlassen
                scMenuExp.close();
                return true;

            default:
                return false;
        }

        return false; //Todo remove
    }

    //TODO create funktionen mit structs realisieren?
    public Journey createJourney() {
        String tempTitle;
        Scanner scJourney = new Scanner(System.in);
        tempTitle = scJourney.nextLine();

        scJourney.close();
        return new Journey(tempTitle, null, null);
    }

    //TODO auf Accomodation umschreiben
    public Place createPlace() {
        Scanner scPlace = new Scanner(System.in);

        System.out.println("Name:");
        String tempName = scPlace.nextLine();

        System.out.println("Latitude:");
        String tempLatitude = scPlace.nextLine();

        System.out.println("Longitude:");
        String tempLongitude = scPlace.nextLine();

        ContactDetails tempCD = new ContactDetails("036167000", "rektorat@fh-erfurt.de", "Erfurt",
                "Altonaer Straße", 25, "99085v", "Deutschland");


        Coordinates tempCoordinates = new Coordinates(tempLatitude, tempLongitude);

        LocalDateTime tempArrival = LocalDateTime.now();
        LocalDateTime tempDeparture = LocalDateTime.now();

        scPlace.close();
/*
        return new Place(tempName,tempCoordinates,tempCD,tempArrival,tempDeparture,null,
                null,null);
                */

        return new Accommodation(null, null, null, null, null,
                null, null, null, null);
    }

/*
    public Person createPerson ()
    {

    }

    public Expense createExpense ()
    {

    }

    public Expense selectExpense ()
    {

    }

    public void alterJourney(Journey selectedJourney){

    }

*/



}
