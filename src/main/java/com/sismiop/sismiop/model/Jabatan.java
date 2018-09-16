package com.sismiop.sismiop.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "jabatans")
public class Jabatan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String role;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "jabatan")
    private List<JabatanPenduduk> jabatanPenduduks = new ArrayList<>();

    private String keterangan;

    public Jabatan() {
    }
    public Jabatan(@NotNull String role, String keterangan) {
        this.role = role;
        this.keterangan = keterangan;
    }
    public Long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    @Override
    public String toString() {
        return this.role;
    }
}
