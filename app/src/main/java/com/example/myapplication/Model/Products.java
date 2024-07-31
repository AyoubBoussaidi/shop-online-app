package com.example.myapplication.Model;

public class Products {

    private String Designation,Description,Categorie,Date,Prix,Image,Pid,Quantite,Temps;

    public Products() {
    }

    public Products(String designation, String description, String categorie, String date, String prix, String image, String pid, String quantite, String temps) {
        Designation = designation;
        Description = description;
        Categorie = categorie;
        Date = date;
        Prix = prix;
        Image = image;
        Pid = pid;
        Quantite = quantite;
        Temps = temps;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCategorie() {
        return Categorie;
    }

    public void setCategorie(String categorie) {
        Categorie = categorie;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getPrix() {
        return Prix;
    }

    public void setPrix(String prix) {
        Prix = prix;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getPid() {
        return Pid;
    }

    public void setPid(String pid) {
        Pid = pid;
    }

    public String getQuantite() {
        return Quantite;
    }

    public void setQuantite(String quantite) {
        Quantite = quantite;
    }

    public String getTemps() {
        return Temps;
    }

    public void setTemps(String temps) {
        Temps = temps;
    }
}
