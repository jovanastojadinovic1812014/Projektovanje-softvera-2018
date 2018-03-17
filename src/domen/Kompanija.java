/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

/**
 *
 * @author Jovana
 */
public class Kompanija {

    private int kompanijaID;
    private String naziv;
    private String maticniBroj;
    private String ulica;
    private String broj;
    private Mesto mesto;

    public Kompanija() {
    }

    public Kompanija(String naziv, String maticniBroj, String ulica, String broj, Mesto mesto) {

        this.naziv = naziv;
        this.maticniBroj = maticniBroj;
        this.ulica = ulica;
        this.broj = broj;
        this.mesto = mesto;
    }

    public Mesto getMesto() {
        return mesto;
    }

    public void setMesto(Mesto mesto) {
        this.mesto = mesto;
    }

    public int getKompanijaID() {
        return kompanijaID;
    }

    public void setKompanijaID(int kompanijaID) {
        this.kompanijaID = kompanijaID;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getMaticniBroj() {
        return maticniBroj;
    }

    public void setMaticniBroj(String maticniBroj) {
        this.maticniBroj = maticniBroj;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public String getBroj() {
        return broj;
    }

    public void setBroj(String broj) {
        this.broj = broj;
    }

    @Override
    public String toString() {
        return "Kompanija{" + "naziv=" + naziv + ", maticniBroj=" + maticniBroj + ", ulica=" + ulica + ", broj=" + broj + ", mesto=" + mesto + '}';
    }
}
