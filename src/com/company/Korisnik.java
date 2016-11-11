package com.company;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


public class Korisnik implements java.io.Serializable // dobijamo gratis Object Svaka Class inherits from Object
{
    static int count = 0;
    enum POL { NOT_AVAILABLE, MALE, FEMALE };
    enum STATUS { ACTIVE, DELETED, BLOCKED};

    public String toString()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-YYYY");
        String sDate = (dob == null) ? "NEMA" : sdf.format(dob);

        return name + "," + email + "," + sDate + ","+ getPol().charAt(0) + "," + "A \n";
    }

    Date dob; //date of birth
// long broji seconds since 1-1-1970

    //TODO Dejana
    // setDob
    // verification dob > 1-1-1900
    // dob < Now - Current date
    // Now - dob > 18 godina

    // string showDob(1,2,3)  // time hh:mm:ss
    // MM-dd-YY     M/d/YYYY   DayOfWeek
    // 11-04-16    11/4/2016   Friday, 321 day of YEar

    //TODO  for loop
    // int kolikoPrestupnihGodina()
    // Now godina % 4 == 0

    private String name;
    //TODO Nusret
    // setName name.len > 2 , name.len < 20
    // svaki karakter unutar stringa name foreach mora biti slovo
    // First char mora biti Uppercase - Capital letter
    // getName return name

    public String getName() { return name; }

    public boolean setName(String ime)
    {
        //validate
        name = ime;
        return true;
    }

    String email;
    //TODO Kenan setEmail split podijeliti getEmail funkciju na unos i validaciju
    // setEmail validacija
    // getEmail

    private String mojaSifra; //min 8 karaktera, mora poceti sa velikim slovom, mala iza toga i zavrsiti sa brojem
    // setSifra validaju
    // getSifra returns (* for each char in email)

    STATUS status;
    private POL pol = POL.NOT_AVAILABLE; // 0 not available 1- male 2 female


    //m ili M za musko, f ili F za zensko
    public void setPol(char c) throws Exception
    {
//        if (c == 'm' || c == 'M') pol = POL.MALE;
//        if (c == 'f' || c == 'F') pol = POL.FEMALE;
        // everything else we don't do anything
        switch(c)
        {
            case 'm':
            case 'M':
                pol = POL.MALE;
                break;

            case 'f':
            case 'F':
                pol = POL.FEMALE;
                break;

            case 'n':
            case 'N':
                pol = POL.NOT_AVAILABLE;
                break;
            default:
                throw new Exception("Error invalid value");
              //  break;

        }
    }

    public String getPol()
    {
        String sPol;
        //pol = 99;  //Error

        if (pol == POL.MALE) {
            sPol = "MALE";
        } else {
            if (pol == POL.FEMALE) {
                sPol = "FEMALE";
            } else {
                sPol = "Not Available";
            }
        }

        return sPol;
    }

    // manje od 8 karaktera a zavrsava sa brojem
    static public void validatePassword(String sifra) throws Exception
    {
        if (sifra.length() >= 8) {
            throw new Exception("Password mora biti kraci od 8 karaktera");

        }

        char zadnji = sifra.charAt(sifra.length() - 1);

        if (!Character.isDigit(zadnji)) {
            throw new Exception("Password se mora zavrsavati sa brojem");
        }

        return;
    }

    public String getSifru() {
        return mojaSifra;
    }

    static String setEmail(String email) throws Exception
    {

        if (!email.contains("@"))  //string email contains @
        {
            throw new Exception(" email mora sadrzati @ karakter");
        }


        int pos = email.indexOf('@');

        int pos1 = email.indexOf('.');
        if ((pos1 - pos) < 3) {
            throw new Exception("Min 2 karaktera iza @ prije .com ");
        }

        // f@bih.ba
        // arif.cengic@guardian.co.uk
        // arif@hotmail.com
        // arif@hotmail.coma !!!
        int posZadnjaTacka = email.lastIndexOf('.');

        int posZadnjiKarakter = email.length() - 1;
        if (!((posZadnjiKarakter - posZadnjaTacka) <= 3)) {
            throw new Exception("domain je od 2 do 3 karaktera iza zadnje tacke");
        }
        if ((posZadnjiKarakter - posZadnjaTacka) < 2) {
            throw new Exception("domain mora imati min 2 karaktera (iza zadnje tacke)");
        }

        if (!(pos <= 64 && pos >= 2)) {
            throw new Exception("moramo imati od 2 do 64 char prije @");
        }
        //  System.out.println("Hello  " + email.substring(0, pos) + " domain je " + email.substring(posZadnjaTacka + 1));

        return email;
    }
}