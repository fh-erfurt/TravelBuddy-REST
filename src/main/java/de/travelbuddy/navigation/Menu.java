package de.travelbuddy.navigation;

import de.travelbuddy.Journey;
import de.travelbuddy.JourneyManager;
import de.travelbuddy.Person;
import de.travelbuddy.place.Place;

import java.util.Scanner;

public class Menu {

    public boolean menuJourneyManager (){

        JourneyManager Test =  new JourneyManager();

        System.out.println("\t-- Journeymanager --\n\n " +
                        "1 - Journey anlegen \n" +
                        "2 - Journey auswählen \n" +
                        "0 - Programm Beenden");

        Scanner scMenu = new Scanner(System.in);
        int menu;
        menu =  scMenu.nextInt();

        switch (menu) {
            case 1:
                Test.AddJourney(createJourney());
                break;

            case 2:
               /*TODO Test.GetJourney(selectJourney())
                menuJourney(selectedJourney)

                */
                break;

            case 0:

                break;

            default:

        }


    }

    public boolean menuJourney(Journey selectedJourney){
        //TODO Journey übersicht anzeigen
        System.out.println("\t-- Journey " + selectedJourney.getTitle() + "--\n\n" +
                "1 - Place anlegen \n" +
                "2 - Place auswählen \n" +
                "3 - Person anlegen \n" +
                "4 - Person auswählen \n" +
                "5 - Journey bearbeiten \n" +
                "6 - Journey löschen \n" +
                "0 - Programm Beenden");

        Scanner scMenuJ = new Scanner(System.in);
        int menu;
        menu =  scMenuJ.nextInt();

        switch (menu){
            case 1:
                selectedJourney.addPlace(createPlace());
                break;

            case 2:
               /*TODO selectedJourney.getPlace(selectPlace())
                menuPlace(selectedPlace)

                */
                break;

            case 0:

                break;

            default:

        }
    }

    public boolean menuPlace(String placeName){
        //TODO Place übersicht anzeigen
        System.out.println("\t-- Place " + placeName + "--\n\n" +
                "1 - Expense anlegen \n" +
                "2 - Expense auswählen \n" +
                "3 - Place bearbeiten \n" +
                "4 - Place löschen \n" +
                "0 - Programm Beenden");
    }

    public boolean menuPerson(String personName){
        System.out.println("\t-- Person " + personName + "--\n\n" +
                "1 - Person bearbeiten \n" +
                "2 - Person löschen \n" +
                "0 - Programm Beenden");

    }

    public boolean menuExpense(String expenseName){
        System.out.println("\t-- Expense " + expenseName + "--\n\n" +
                "1 - Person hinzufügen \n" +
                "2 - Person löschen \n" +
                "3 - Expense bearbeiten \n" +
                "0 - Programm Beenden");

    }

    public Journey createJourney ()
    {
        String tempTitle;
        Scanner scJourney = new Scanner(System.in);
        tempTitle = scJourney.nextLine();

        return new Journey(tempTitle,null,null);
    }

    public Place createPlace ()
    {


    }

    public Person createPerson ()
    {

    }



}
