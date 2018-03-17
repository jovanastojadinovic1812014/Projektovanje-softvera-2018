/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import domen.Kompanija;
import domen.Mesto;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.SettingsLoader;

/**
 *
 * @author Jovana
 */
public class DBKomunikacija {
    
    private Connection connection;
    
    public void ucitajDriver() throws Exception {
        try {
            Class.forName(SettingsLoader.getInstance().getValue("driver"));
        } catch (ClassNotFoundException ex) {
            throw new Exception("Driver nije ucitan! " + ex.getMessage());
        }
    }
    
    public void otvoriKonekciju() throws Exception {
        try {
            //ucitavanje iz propertie fajla
            String url = SettingsLoader.getInstance().getValue("url");
            String user = SettingsLoader.getInstance().getValue("user");
            //izbacuje gresku kada prazan string prosledimo iz propertie fajla
            //String password = SettingsLoader.getInstance().getValue("password");

            //otvaranje konekcije, imamo potrebne parametre
            connection = DriverManager.getConnection(url, user, "");
            
            connection.setAutoCommit(false);
            //sada je potrebna eksplicitna potvrda transakcije
        } catch (IOException | SQLException ex) {
            throw new Exception("Konekcija nije uspostavljena! " + ex.getMessage());
        }
        
    }
    
    public void zatvoriKonekciju() throws Exception {
        try {
            connection.close();
        } catch (SQLException ex) {
            throw new Exception("Konekcija nije uspesno zatvorena! " + ex.getMessage());
        }
    }
    
    public void commitTransakcije() throws Exception {
        try {
            connection.commit();
        } catch (SQLException ex) {
            throw new Exception("Neuspesan commit transakcije! " + ex.getMessage());
        }
    }
    
    public void rollbackTransakcije() throws Exception {
        try {
            connection.rollback();
        } catch (SQLException ex) {
            throw new Exception("Neuspesan rollback transakcije! " + ex.getMessage());
        }
    }
    
    public List<Kompanija> vratiKompanijeJOIN() throws Exception {
        
        List<Kompanija> listaKompanija = new LinkedList<>();
        
        try {
            String upit = "SELECT kompanija.naziv, kompanija.maticniBroj, kompanija.ulica, kompanija.broj, "
                    + "mesto.ptt, mesto.naziv AS nazivMesta "
                    + "FROM kompanija JOIN mesto ON kompanija.ptt=mesto.ptt";
            
            PreparedStatement ps = connection.prepareStatement(upit);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                String naziv = rs.getString("naziv");
                String maticniBroj = rs.getString("maticniBroj");
                String ulica = rs.getString("ulica");
                String broj = rs.getString("broj");
                int ptt = rs.getInt("ptt");
                String nazivMesta = rs.getString("nazivMesta");
                
                Mesto mesto = new Mesto(ptt, nazivMesta);
                Kompanija k = new Kompanija(naziv, maticniBroj, ulica, broj, mesto);
                listaKompanija.add(k);
            }
            System.out.println(listaKompanija);
            return listaKompanija;
        } catch (SQLException ex) {
            throw new Exception("Kompanije nisu uspesno izlistane! " + ex.getMessage());
        }
        
    }
    
    public Kompanija vratiKompaniju(int id) throws Exception {
        String upit = "SELECT * FROM kompanija WHERE kompanijaID=?";
        PreparedStatement ps = connection.prepareStatement(upit);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String naziv = rs.getString("naziv");
            String maticniBroj = rs.getString("maticniBroj");
            String ulica = rs.getString("ulica");
            String broj = rs.getString("broj");
            int ptt = rs.getInt("ptt");
            
            Mesto m = vratiMesto(ptt);
            Kompanija k = new Kompanija(naziv, maticniBroj, ulica, broj, m);
            System.out.println(k);
            return k;
        }
        throw new Exception("Ne postoji kompanija sa tim id-jem!");
        
    }
    
    public List<Mesto> vratiMesta() throws Exception {
        
        List<Mesto> listaMesta = new LinkedList<>();
        
        try {
            String upit = "SELECT * FROM mesto";
            
            PreparedStatement ps = connection.prepareStatement(upit);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                int ptt = rs.getInt("ptt");
                String naziv = rs.getString("naziv");
                
                Mesto m = new Mesto(ptt, naziv);
                listaMesta.add(m);
            }
            
            System.out.println(listaMesta);
            return listaMesta;
        } catch (SQLException ex) {
            throw new Exception("Mesta nisu uspesno izlistana! " + ex.getMessage());
        }
        
    }
    
    public List<Kompanija> vratiKompanijeMetoda() throws Exception {
        List<Kompanija> listaKompanija = new LinkedList<>();
        
        String upit = "SELECT * FROM kompanija";
        
        try {
            PreparedStatement ps = connection.prepareStatement(upit);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                String naziv = rs.getString("naziv");
                String maticniBroj = rs.getString("maticniBroj");
                String ulica = rs.getString("ulica");
                String broj = rs.getString("broj");
                int ptt = rs.getInt("ptt");
                
                Mesto m = vratiMesto(ptt);
                Kompanija k = new Kompanija(naziv, maticniBroj, ulica, broj, m);
                listaKompanija.add(k);
            }
            System.out.println(listaKompanija);
            return listaKompanija;
        } catch (SQLException ex) {
            throw new Exception("Kompanije nisu uspesno izlistane! " + ex.getMessage());
        }
        
    }

    // vrati mesto sa zadatim ptt-om nije odradjena metoda preko try catch bloka!
    public Mesto vratiMesto(int ptt) throws Exception {
        String upit = "SELECT * FROM mesto WHERE ptt=?";
        
        PreparedStatement ps = connection.prepareStatement(upit);
        ps.setInt(1, ptt);
        
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String naziv = rs.getString("naziv");
            Mesto m = new Mesto(ptt, naziv);
            return m;
        }
        
        throw new Exception("Nije pronadjeno mesto sa zadatim ptt-om!");
    }
    
    public void sacuvajKompaniju(Kompanija k) throws Exception {
        String upit = "INSERT INTO kompanija(naziv, maticniBroj, ulica, broj, ptt) VALUES(?,?,?,?,?)";
        
        try {

            // preparedStatement
            PreparedStatement ps = connection.prepareStatement(upit, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, k.getNaziv());
            ps.setString(2, k.getMaticniBroj());
            ps.setString(3, k.getUlica());
            ps.setString(4, k.getBroj());
            ps.setInt(5, k.getMesto().getPtt());

            //pozivamo executeUpdate jer nam nije potreban resultSet
            ps.executeUpdate();

            //ukoliko smo kompaniju ubacili u bazu moramo da pronadjemo i generisani id
            ResultSet rs = ps.getGeneratedKeys();
            //ukoliko postoji pamtimo ga u polje kompanijaID u objektu klase kompanija
            if (rs.next()) {
                int kompanijaID = rs.getInt(1);
                k.setKompanijaID(kompanijaID);
            } else {
                throw new Exception("Kompanija nije generisana u bazi!");
            }
            
        } catch (SQLException ex) {
            throw new Exception("Kompanija nije uspesno sacuvana! " + ex.getMessage());
        }
        
    }
    
    public void sacuvajMesto(Mesto m) throws Exception {
        try {
            
            String upit = "INSERT INTO mesto(ptt, naziv) VALUES (?,?)";
            PreparedStatement ps = connection.prepareStatement(upit);
            ps.setInt(1, m.getPtt());
            ps.setString(2, m.getNaziv());
            
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            throw new Exception("Mesto nije sacuvano uspesno! " + ex.getMessage());
        }
    }
}
