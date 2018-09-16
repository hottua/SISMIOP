package com.sismiop.sismiop.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "spop")
public class Spop extends AuditModel{
    public Spop() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer nop;
    private Integer nopBersama;
    private Integer nopAsal;
    private Integer nopSPPTLama;
    private String namaJalanOP;
    private String blokOP;
    private String kelurahaOP;
    private String rtop;
    private String rwop;
    private String pekerjaanSP;
    private String statusSP;
    private String namaSP;
    private Integer npwp;
    private String namaJalanSP;
    private String blokSP;
    private String kelurahanSP;
    private String rtsp;
    private String rwsp;
    private String luasTanah;
    private String zonaNilaiTanah;
    private String jenisTanah;
    private String fileIdentitasDiri;
    private String fileBuktiSuratTanah;
    private String fileBuktiSuratBangunan;
    private String fileNpwp;
    private String fileSuratKuasa;
    private double njopLuasTanah;
    private double njopLuasBangunan;
    private boolean statusSppt = false;

    public boolean isStatusSppt() {
        return statusSppt;
    }

    public void setStatusSppt(boolean statusSppt) {
        this.statusSppt = statusSppt;
    }

    public double getNjopLuasTanah() {
        return njopLuasTanah;
    }

    public void setNjopLuasTanah(double njopLuasTanah) {
        this.njopLuasTanah = njopLuasTanah;
    }

    public double getNjopLuasBangunan() {
        return njopLuasBangunan;
    }

    public void setNjopLuasBangunan(double njopLuasBangunan) {
        this.njopLuasBangunan = njopLuasBangunan;
    }

    public String getFileIdentitasDiri() {
        return fileIdentitasDiri;
    }

    public void setFileIdentitasDiri(String fileIdentitasDiri) {
        this.fileIdentitasDiri = fileIdentitasDiri;
    }

    public String getFileBuktiSuratTanah() {
        return fileBuktiSuratTanah;
    }

    public void setFileBuktiSuratTanah(String fileBuktiSuratTanah) {
        this.fileBuktiSuratTanah = fileBuktiSuratTanah;
    }

    public String getFileBuktiSuratBangunan() {
        return fileBuktiSuratBangunan;
    }

    public void setFileBuktiSuratBangunan(String fileBuktiSuratBangunan) {
        this.fileBuktiSuratBangunan = fileBuktiSuratBangunan;
    }

    public String getFileNpwp() {
        return fileNpwp;
    }

    public void setFileNpwp(String fileNpwp) {
        this.fileNpwp = fileNpwp;
    }

    public String getFileSuratKuasa() {
        return fileSuratKuasa;
    }

    public void setFileSuratKuasa(String fileSuratKuasa) {
        this.fileSuratKuasa = fileSuratKuasa;
    }

    @OneToOne
    @JoinColumn(name = "permohonan_op_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PermohonanOp permohonanOp;

    public Long getId() {
        return id;
    }



    public Integer getNop() {
        return nop;
    }

    public void setNop(Integer nop) {
        this.nop = nop;
    }

    public Integer getNopBersama() {
        return nopBersama;
    }

    public void setNopBersama(Integer nopBersama) {
        this.nopBersama = nopBersama;
    }

    public Integer getNopAsal() {
        return nopAsal;
    }

    public void setNopAsal(Integer nopAsal) {
        this.nopAsal = nopAsal;
    }

    public Integer getNopSPPTLama() {
        return nopSPPTLama;
    }

    public void setNopSPPTLama(Integer nopSPPTLama) {
        this.nopSPPTLama = nopSPPTLama;
    }

    public String getNamaJalanOP() {
        return namaJalanOP;
    }

    public void setNamaJalanOP(String namaJalanOP) {
        this.namaJalanOP = namaJalanOP;
    }

    public String getBlokOP() {
        return blokOP;
    }

    public void setBlokOP(String blokOP) {
        this.blokOP = blokOP;
    }

    public String getKelurahaOP() {
        return kelurahaOP;
    }

    public void setKelurahaOP(String kelurahaOP) {
        this.kelurahaOP = kelurahaOP;
    }

    public String getRtop() {
        return rtop;
    }

    public void setRtop(String rtop) {
        this.rtop = rtop;
    }

    public String getRwop() {
        return rwop;
    }

    public void setRwop(String rwop) {
        this.rwop = rwop;
    }

    public String getPekerjaanSP() {
        return pekerjaanSP;
    }

    public void setPekerjaanSP(String pekerjaanSP) {
        this.pekerjaanSP = pekerjaanSP;
    }

    public String getStatusSP() {
        return statusSP;
    }

    public void setStatusSP(String statusSP) {
        this.statusSP = statusSP;
    }

    public String getNamaSP() {
        return namaSP;
    }

    public void setNamaSP(String namaSP) {
        this.namaSP = namaSP;
    }

    public Integer getNpwp() {
        return npwp;
    }

    public void setNpwp(Integer npwp) {
        this.npwp = npwp;
    }

    public String getNamaJalanSP() {
        return namaJalanSP;
    }

    public void setNamaJalanSP(String namaJalanSP) {
        this.namaJalanSP = namaJalanSP;
    }

    public String getBlokSP() {
        return blokSP;
    }

    public void setBlokSP(String blokSP) {
        this.blokSP = blokSP;
    }

    public String getKelurahanSP() {
        return kelurahanSP;
    }

    public void setKelurahanSP(String kelurahanSP) {
        this.kelurahanSP = kelurahanSP;
    }

    public String getRtsp() {
        return rtsp;
    }

    public void setRtsp(String rtsp) {
        this.rtsp = rtsp;
    }

    public String getRwsp() {
        return rwsp;
    }

    public void setRwsp(String rwsp) {
        this.rwsp = rwsp;
    }

    public String getLuasTanah() {
        return luasTanah;
    }

    public void setLuasTanah(String luasTanah) {
        this.luasTanah = luasTanah;
    }

    public String getZonaNilaiTanah() {
        return zonaNilaiTanah;
    }

    public void setZonaNilaiTanah(String zonaNilaiTanah) {
        this.zonaNilaiTanah = zonaNilaiTanah;
    }

    public String getJenisTanah() {
        return jenisTanah;
    }

    public void setJenisTanah(String jenisTanah) {
        this.jenisTanah = jenisTanah;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public PermohonanOp getPermohonanOp() {
        return permohonanOp;
    }

    public void setPermohonanOp(PermohonanOp permohonanOp) {
        this.permohonanOp = permohonanOp;
    }
}
