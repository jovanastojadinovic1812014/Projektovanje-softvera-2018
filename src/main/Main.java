/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import db.DBKomunikacija;
import domen.Kompanija;
import domen.Mesto;

/**
 *
 * @author Jovana
 */
public class Main {

    public static void main(String[] args) throws Exception {
        //pravimo instancu klase DBKomunikacija
        DBKomunikacija dbKom = new DBKomunikacija();

        //ucitavamo drajver
        dbKom.ucitajDriver();

        //otvaramo konekciju
        dbKom.otvoriKonekciju();

        //izvrsavamo operacije
        
        Mesto m = new Mesto(35230, "Cuprija");
        dbKom.sacuvajMesto(m);
        Kompanija k = new Kompanija("Jovanina2", "0608", "ul", "bb", m);
        dbKom.sacuvajKompaniju(k);
        System.out.println(k);
        
        //potvrdjujemo transakcije
        dbKom.commitTransakcije();

        //zatvaramo konekciju
        dbKom.zatvoriKonekciju();

    }
}
