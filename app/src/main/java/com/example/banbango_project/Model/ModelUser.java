package com.example.banbango_project.Model;

import com.google.gson.annotations.SerializedName;

public class ModelUser {
    @SerializedName("id")
    private int id;
    @SerializedName("nama")
    private String nama;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("kontak")
    private String kontak;

    public ModelUser(int id, String nama, String email, String password, String kontak) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.kontak = kontak;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKontak() {
        return kontak;
    }

    public void setKontak(String kontak) {
        this.kontak = kontak;
    }
}
