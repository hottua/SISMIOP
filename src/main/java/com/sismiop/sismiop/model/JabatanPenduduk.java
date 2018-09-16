package com.sismiop.sismiop.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class JabatanPenduduk extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jabatan_id")
    private Jabatan jabatan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "penduduk_id")
    private Penduduk penduduk;

    public JabatanPenduduk(Penduduk penduduk, Jabatan jabatan) {
        this.jabatan = jabatan;
        this.penduduk = penduduk;
    }

    public JabatanPenduduk() {

    }

    @Override
    public String toString() {
        return jabatan.getRole();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Jabatan getJabatan() {
        return jabatan;
    }

    public void setJabatan(Jabatan jabatan) {
        this.jabatan = jabatan;
    }

    public Penduduk getPenduduk() {
        return penduduk;
    }

    public void setPenduduk(Penduduk penduduk) {
        this.penduduk = penduduk;
    }
}
