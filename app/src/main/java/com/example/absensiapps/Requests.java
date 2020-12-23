package com.example.absensiapps;

import java.io.Serializable;

public class Requests implements Serializable {

    private String nama;
    private String kelas;
    private String jurusan;


    private String key;

    public Requests(){

    }

    public Requests(String nama, String kelas, String jurusan){
        this.nama= nama;
        this.kelas = kelas;
        this.jurusan = jurusan;
    }
     public String getNama() {return nama; }

     public void setNama(String nama) {this.nama= nama;}

    public String getKelas() {return kelas; }

    public void setKelas(String kelas) {this.kelas = kelas;}

    public String getJurusan() {return jurusan; }

    public void setJurusan(String jurusan) {this.jurusan = jurusan;}

    public String getKey() {return key; }

    public void setKey(String key) {this.key= key;}


    @Override
    public String toString() {
        return " " + nama + "\n" +
                " " + kelas + "\n" +
                " " + jurusan;
    }
}