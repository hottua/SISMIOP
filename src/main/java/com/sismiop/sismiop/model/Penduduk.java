package com.sismiop.sismiop.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@Entity
@Table(name = "penduduks")
public class Penduduk {
    public Penduduk() {
    }



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotEmpty(message = "*NIK tidak boleh kosong")
    private String nik;
    @Column(unique = true)
    @NotEmpty(message = "*NPWP tidak boleh kosong")
    private String npwp;

    @Column(unique = true)
    @NotEmpty(message = "*Nomor telepon/Hp tidak boleh kosong")
    private String noTelp;

    @NotEmpty(message = "*Nama tidak boleh kosong")
    private String nama;
    private String alamat;
    private String jk;
    private String agama;
    private String pekerjaan;
    private String statusPerkawinan;
    private String golDarah;
    private Date tglLahir;


    @Length(min = 5, message = "*Minimal password 5 karakter")
    @NotEmpty(message = "*password tidak boleh kosong")
    private String password;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "penduduk")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<JabatanPenduduk> jabatanPenduduks = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNpwp() {
        return npwp;
    }

    public void setNpwp(String npwp) {
        this.npwp = npwp;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getJk() {
        return jk;
    }

    public void setJk(String jk) {
        this.jk = jk;
    }

    public String getAgama() {
        return agama;
    }

    public void setAgama(String agama) {
        this.agama = agama;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(String pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

    public String getStatusPerkawinan() {
        return statusPerkawinan;
    }

    public void setStatusPerkawinan(String statusPerkawinan) {
        this.statusPerkawinan = statusPerkawinan;
    }

    public String getGolDarah() {
        return golDarah;
    }

    public void setGolDarah(String golDarah) {
        this.golDarah = golDarah;
    }

    public Date getTglLahir() {
        return tglLahir;
    }

    public void setTglLahir(Date tglLahir) {
        this.tglLahir = tglLahir;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<JabatanPenduduk> getJabatanPenduduks() {
        return jabatanPenduduks;
    }

    public void setJabatanPenduduks(List<JabatanPenduduk> jabatanPenduduks) {
        this.jabatanPenduduks = jabatanPenduduks;
    }
}
