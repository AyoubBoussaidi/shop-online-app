package com.example.myapplication.Model;

public class AdminOrders {
    private String Adresse,Date,Nom,Telephone,Time,Ville,Montant_Total;

    public AdminOrders() {
    }

    public AdminOrders(String adresse, String date, String nom, String telephone, String time, String ville, String montant_Total) {
        Adresse = adresse;
        Date = date;
        Nom = nom;
        Telephone = telephone;
        Time = time;
        Ville = ville;
        Montant_Total = montant_Total;
    }

    public String getAdresse() {
        return Adresse;
    }

    public void setAdresse(String adresse) {
        Adresse = adresse;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getVille() {
        return Ville;
    }

    public void setVille(String ville) {
        Ville = ville;
    }

    public String getMontant_Total() {
        return Montant_Total;
    }

    public void setMontant_Total(String montant_Total) {
        Montant_Total = montant_Total;
    }
}
