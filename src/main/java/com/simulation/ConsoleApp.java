package com.simulation;

import java.util.Scanner;

/**
 * optional console interface
 */
public class ConsoleApp {
    /**
     * main menu for console interface
     */
    static void menu(){
        int uChoice;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Witaj w symulacji! Wybierz działanie: ");
        do {
            System.out.println("=============== MENU GLÓWNE ===============");
            System.out.println("[0] wyjście");
            System.out.println("[1] wyświetl/edytuj parametry");
            System.out.println("[2] rozpocznij symulację");
            System.out.print("Twój wybór: ");
            while(!scanner.hasNextInt()) {
                scanner = new Scanner(System.in);
                System.out.print("Podano nieprawidłową wartość, spróbuj ponownie: ");
            }
            uChoice = scanner.nextInt();
            System.out.println();

            switch(uChoice){
                case 0 -> System.out.println("Do zobaczenia :)");
                case 1 -> Simulation.printParams();
                case 2 -> {writingConfiguration(); Simulation.simulation();}
                default -> System.out.println("niepoprawna wartość!");
            }

        }while(uChoice != 0 && uChoice != 2);

    }


    /**
     * takes user input and sets what should be written to results file. Works for console interface
     */
    static void writingConfiguration(){
        boolean[] ansFile = new boolean[6];
        do {
            int fileChoice;
            System.out.println("Wybierz jakie dane chcesz zapisać w pliku tekstowym: ");
            System.out.println("[0] - Przejdź do symulacji");
            System.out.println("[1] - Śmierci piechoty w iteracji");
            System.out.println("[2] - Zniszczenia czołgu w iteracji");
            System.out.println("[3] - Zniszczenia pułapki w iteracji");
            System.out.println("[4] - Wykorzystania zasobów mobilnej bazy w iteracji");
            System.out.println("[5] - Szczegółowe dane odnośnie bitew");
            Scanner scannerFileChoice = new Scanner(System.in);
            while (!scannerFileChoice.hasNextInt()) {
                scannerFileChoice = new Scanner(System.in);
                System.out.print("Podano nieprawidłową wartość, spróbuj ponownie: ");
            }
            fileChoice = scannerFileChoice.nextInt();

            switch (fileChoice) {
                case 0:
                    ansFile[fileChoice] = true;
                    break;
                case 1:
                    if (ansFile[fileChoice]) {
                        System.out.println("Już wybrałeś tą pozycje. Wybierz inną lub rozpocznij symulację");
                    } else {
                        ansFile[fileChoice] = true;
                        System.out.println("W pliku zapiszesz dane odnośnie śmierci piechoty w iteracji");
                    }
                    break;
                case 2:
                    if (ansFile[fileChoice]) {
                        System.out.println("Już wybrałeś tą pozycje. Wybierz inną lub rozpocznij symulację");
                    } else {
                        ansFile[fileChoice] = true;
                        System.out.println("W pliku zapiszesz dane odnośnie zniszczenia czołgu w iteracji");
                    }
                    break;
                case 3:
                    if (ansFile[fileChoice]) {
                        System.out.println("Już wybrałeś tą pozycje. Wybierz inną lub rozpocznij symulację");
                    } else {
                        ansFile[fileChoice] = true;
                        System.out.println("W pliku zapiszesz dane odnośnie zniszczenia pułapki w iteracji");
                    }
                    break;
                case 4:
                    if (ansFile[fileChoice]) {
                        System.out.println("Już wybrałeś tą pozycje. Wybierz inną lub rozpocznij symulację");
                    } else {
                        ansFile[fileChoice] = true;
                        System.out.println("W pliku zapiszesz dane odnośnie wykorzystania zasobów mobilnej bazy w iteracji");
                    }
                    break;
                case 5:
                    if (ansFile[fileChoice]) {
                        System.out.println("Już wybrałeś tą pozycje. Wybierz inną lub rozpocznij symulację");
                    } else {
                        ansFile[fileChoice] = true;
                        System.out.println("W pliku zapiszesz Szczegółowe dane odnośnie bitew");
                    }
                    break;
                default:
                    System.out.println("Niepoprawna wartość, spróbuj jeszcze raz");
            }
        } while (!ansFile[0]);

        System.arraycopy(ansFile, 1, Simulation.uFileChoice, 0, 5);

    }
}
