package com.majinor.esportal.model;

public class DataMet {

    String foto;
    String nama;
    String jenis;
    String tempat;
    String date;

    public DataMet(String foto, String nama, String jenis, String tempat,String date) {
        this.foto =foto;
        this.nama = nama;
        this.jenis = jenis;
        this.tempat= tempat;
        this.date = date;
    }

    public String getFoto() {
        return foto;
    }
    public String getNama() {
        return nama;
    }

    public String getJenis() {
        return jenis;
    }

    public String getTempat() {
        return tempat;
    }
    public String getDate() {
        return date;
    }
}