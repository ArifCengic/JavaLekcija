package com.company;

import com.sun.webkit.WebPageClient;
import javafx.scene.input.DataFormat;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    enum IZBOR { NA, NoviKorisnik, IzbrisatiKorisnika, IzlistatiSveKorisnike, PronadjiKorisnika, ExitProgram}

    ;
    static ArrayList<Korisnik> korisnici = new ArrayList<>();

    public static void main(String[] args) {
        //   Scanner in = new Scanner(System.in);

        writeToFile();
        readFromFile();
        int izbor = 0;
        do {
            //TODO clear screen before showing all options
            System.out.println("IZBOR ");
            System.out.println("1: Kreirati Novog Korisnika");
            System.out.println("2: Izbrisati Korisnika");
            System.out.println("3: Izlistati Sve Korisnike");
            System.out.println("4: Pronadji Korisnika");
            System.out.println("5: Pronadji Korisnika by email");
            System.out.println("6: Delete Korisnika by email");

            System.out.println("7: Exit Program ");
            System.out.println("---");
            Scanner in = new Scanner(System.in);
            try {
                izbor = in.nextInt();

            switch (izbor) {
                case 1:
                    noviKorisnik();
                    break;

                case 2:
                    izbrisatiKorisnik();
                    break;

                case 3:
                    izlistatiSveKorisnike();
                    break;

                case 4: {
                    int pos = pronadjiKorisnika();
                    if (pos > -1) {
                        System.out.println("Korisnik je pronadjen na indexu " + pos);
                    } else {
                        System.out.println("Korisnik nije pronadjen");
                    }
                }
                break;

                case 5:
                    //TODO call function nadjiKorisnikaByEmail
                    break;

                case 6:
                    //TODO
                    break;
                case 7:
                    exitProgram();
                    SerializeKorisnike(korisnici);
                    return;
                 //   break;

                default:
                    throw  new Exception("Unijeli ste pogresan izbor. Mora biti 1-7");
                    //break;
            }

            } //end try
            catch (InputMismatchException e)
            {
                System.out.println("Unesite broj");
                continue;
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
                continue;
            }

        } while (izbor != 5);

        //izbor == 5

    }

    static int prebrojatiPrestupneGodine(int startYear) throws Exception {
        // int startYear = 2000
        // String message = ""
        // Korisnik a =
        int countGodine = 0;
        Date d = new Date();
        int currentYear = 2016;

        if (startYear > currentYear) {
            throw new Exception("Pocetna godina mora biti manja od " + currentYear);
        }
        //long broj msec 1-1-1970
        for (int i = startYear; i <= currentYear; i++) {
            if (i % 4 == 0) {
                countGodine++;
            }
        }
        //broj prestupnih godina izmedju startYear i Current Year 2016
        return countGodine;
    }

    static void writeToFile()
    {
        try {
            FileWriter fileOut = new FileWriter("Korisnik.txt");
            BufferedWriter out = new BufferedWriter(fileOut);
            out.write("Test Korisnik");
            System.out.printf("Data is saved in Korisnik.txt");
            out.close();
            fileOut.close();
        }
        catch (IOException i)
        {
            i.printStackTrace();
        }
    }

    static void readFromFile()
    {
        try {
            FileReader fileIn = new FileReader("Korisnik.txt");
            BufferedReader in = new BufferedReader(fileIn);
            String line = in.readLine();
            System.out.printf(line);
            in.close();
            fileIn.close();
        }
        catch (IOException i)
        {
            i.printStackTrace();
        }
    }


    static void noviKorisnik() {
         Scanner in = new Scanner(System.in);

        System.out.println("Unesite Ime Korisnika");
        String ime = in.nextLine();

        System.out.println("Unesite email Korisnika");
        String email;
        do {
            try {
                String unos;
                System.out.print("\n Unesite email \n");
               // Scanner in = new Scanner(System.in);
                unos = in.nextLine();
                
                email = Korisnik.setEmail(unos);
                break;
            } catch (Exception e) {
                System.out.println("Email nije validan. " + e.getMessage());
                continue;
            }

        } while (true);

        Korisnik novi = new Korisnik();
        novi.setName(ime);
        novi.email = email;

        korisnici.add(novi);

    }

    // if korisnik is found & deleted return true
    // if korisnik with ime is not found return false
    static boolean izbrisatiKorisnik() {
        System.out.println("Brisati Korisnika");
        int pos = pronadjiKorisnika();
        if (pos > -1) {
            System.out.println("Korisnik je pronadjen na indexu " + pos);
            korisnici.remove(pos - 1);
            return true;
        } else {
            System.out.println("Korisnik nije pronadjen");
            return false;
        }


    }

    static void izlistatiSveKorisnike() {
        System.out.println("Svi Korisnici");

        //Start        Stop                     Step
        for (int count = 0; count < korisnici.size(); count++) {
            Korisnik k = korisnici.get(count);
            System.out.println( k.toString());
        }

        //foreach
//        for (Korisnik k:korisnici) {
//            count++;
//            System.out.println(count + "-Ime " + k.getName() + " email " + k.email + " sex " + k.getPol());
//        }
    }

    static int pronadjiKorisnika() {
//        String name = unesiKorisnika();
//        int result = nadjiKorisnika(name);
//        return result;

        return nadjiKorisnika(unesiKorisnika()); //isto kao prethodne 3 linije
    }


    static String unesiKorisnika() {
        // System.out.println("pronadjiKorisnika");
        Scanner in = new Scanner(System.in);
        System.out.println("Unesite Ime Korisnika");
        String ime = in.nextLine();
        return ime;
    }

    static int nadjiKorisnika(String ime) // -1 nije nasao
    {
        int count = 0;
        for (Korisnik k : korisnici) {
            count++;
            if (k.getName().equals(ime)) return count;
        }

        return -1;
    }

    static void exitProgram() {
        System.out.println("exitProgram");
    }

    static void SerializeKorisnike(ArrayList<Korisnik> korisnici) {

        try {
            FileOutputStream fileOut =
                    new FileOutputStream("/tmp/korisnici.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(korisnici);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in /tmp/korisnici.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    static void DeserializeKorisnike() {
        {
            try {
                FileInputStream fileIn = new FileInputStream("/tmp/korisnici.ser");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                korisnici = (ArrayList<Korisnik>) in.readObject();
                in.close();
                fileIn.close();
            } catch (IOException i) {
                i.printStackTrace();
                return;
            } catch (ClassNotFoundException c) {
                System.out.println("Employee class not found");
                c.printStackTrace();
                return;
            }


        }

    }

}