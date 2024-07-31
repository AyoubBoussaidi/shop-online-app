package com.example.myapplication.Model;

public class Panier {
    private String Pid,Pdesignation,Prix,Quantite,Remise;

    public Panier() {
    }
    public Panier(String pid, String pdesignation, String prix, String quantite, String remise) {
        Pid = pid;
        Pdesignation = pdesignation;
        Prix = prix;
        Quantite = quantite;
        Remise = remise;
    }

    public String getPid() {
        return Pid;
    }

    public void setPid(String pid) {
        Pid = pid;
    }

    public String getPdesignation() {
        return Pdesignation;
    }

    public void setPdesignation(String pdesignation) {
        Pdesignation = pdesignation;
    }

    public String getPrix() {
        return Prix;
    }

    public void setPprix(String pprix) {
        Prix = pprix;
    }

    public String getQuantite() {
        return Quantite;
    }

    public void setPQuantite(String quantite) {
        Quantite = quantite;
    }

    public String getRemise() {
        return Remise;
    }

    public void setRemise(String remise) {
        Remise = remise;
    }
}
