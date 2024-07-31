package com.example.myapplication.Model;


public class Users {
    private String Login, Password, CIN,Telephone,Image,Nom_Prenom;

    public Users()
    {

    }

    public Users(String login,  String password,String cin,String telephone,String image,String name) {
        this.Login= login;
        this.Password = password;
        this.CIN=cin;
        this.Telephone=telephone;
        this.Image=image;
        this.Nom_Prenom=name;
    }

    public String getLogin() {
        return Login;
    }

    public void setName(String login) {
        this.Login = login;
    }

    public String getPassword() {
        return Password;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getCIN() {
        return CIN;
    }

    public void setCIN(String cin) {
        CIN = cin;
    }

    public String getTelephone() {
        return Telephone;
    }
    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public String getImage() {
        return Image;
    }

    public String getNom_Prenom() {
        return Nom_Prenom;
    }

    public void setNom_Prenom(String nom_Prenom) {
        Nom_Prenom = nom_Prenom;
    }

    public void setImage(String image) {
        Image = image;
    }
}
