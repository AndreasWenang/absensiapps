package com.example.absensiapps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.Serializable;

public class Requests implements Serializable {

    private String nama;
    private String email;
    private String deskripsi;

    private String key;

    public Requests(){

    }

    public Requests(String nama, String email, String deskripsi){
        this.nama= nama;
        this.email= email;
        this.deskripsi= deskripsi;
    }
     public String getNama() {return nama; }

     public void setNama(String nama) {this.nama= nama;}

    public String getEmail() {return email; }

    public void setEmail(String email) {this.email= email;}

    public String getDeskripsi() {return deskripsi; }

    public void setDeskripsi(String deskripsi) {this.deskripsi= deskripsi;}

    public String getKey() {return key; }

    public void setKey(String key) {this.key= key;}


    @Override
    public String toString() {
        return " " + nama + "\n" +
                " " + email + "\n" +
                " " + deskripsi ;
    }
}