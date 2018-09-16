package com.sismiop.sismiop.controller;

import com.sismiop.sismiop.model.*;
import com.sismiop.sismiop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.management.relation.Role;
import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private PermohonanOpService permohonanOpService;

    @Autowired
    private PendudukService pendudukService;

    @Autowired
    private JabatanService jabatanService;

    @Autowired
    private JabatanPendudukService jabatanPendudukService;

    @Autowired
    private SpopService spopService;

    @GetMapping("permohonan-op-penduduk/daftar")
    public ModelAndView getAllDaftarPermohonanOp(@PageableDefault(size = 15)Pageable pageable) {
        ModelAndView m = new ModelAndView();
        m.addObject("subTitle", "Daftar Permohonan OP Penduduk");
        Page<PermohonanOp> page =  permohonanOpService.getAllPermohonan(pageable);
        m.addObject("page", page);
        m.setViewName("pages/admin/daftar-semua-permohonan-op");
        return  m;
    }

    @GetMapping("permohonan-op-penduduk/setujui/{id}")
    public ModelAndView setujuiPermohonanOp(@PathVariable("id") Long id) {
        ModelAndView m = new ModelAndView();
        if(permohonanOpService.updateStatus(id, true)) {
            m.addObject("msg", "Permohonan berhasil disetujui");
        }else {
            m.addObject("msg", "Permohonan Gagal di disetujui");

        }
        m.setViewName("redirect:/admin/permohonan-op-penduduk/daftar");
        return  m;
    }

    @GetMapping("permohonan-op-penduduk/batal-menyetujui/{id}")
    public ModelAndView batalMenyetujuiPermohonanOp(@PathVariable("id") Long id) {
        ModelAndView m = new ModelAndView();
        if(permohonanOpService.updateStatus(id, false)) {
            m.addObject("msg", "Permohonan berhasil disetujui");
        }else {
            m.addObject("msg", "Permohonan Gagal disetujui");

        }
        m.setViewName("redirect:/admin/permohonan-op-penduduk/daftar");
        return  m;
    }

    @GetMapping("penduduk/daftar-penduduk")
    public ModelAndView daftarPenduduk(@PageableDefault(size = 15)Pageable pageable){
        ModelAndView m = new ModelAndView();
        m.addObject("subTitle", "Tambah Staff");
        Page<Penduduk> page =  pendudukService.getAllPenduduk(pageable);
        m.addObject("page", page);
        m.setViewName("pages/admin/tambah-staff");
        return  m;
    }

    @PostMapping("penduduk/daftar-penduduk")
    public ModelAndView addStaff(@RequestParam("idPenduduk") Long idPenduduk,
                                 @RequestParam("role") String role){
        ModelAndView m = new ModelAndView();
        Jabatan j = jabatanService.getByRole(role);
        Penduduk p = pendudukService.getPendudukById(idPenduduk);
        JabatanPenduduk jp = jabatanPendudukService.getJabatanPendudukByPendudukId(idPenduduk);
        System.out.println("===================================");
        System.out.println("Id : "+idPenduduk);
        System.out.println("Nama : "+p.getNama());
        System.out.println("===================================");
        jp.setJabatan(j);
        jp.setPenduduk(p);
        jabatanPendudukService.update(jp);
        m.setViewName("redirect:/admin/penduduk/daftar-penduduk");
        return  m;
    }

    @GetMapping("spop/daftar")
    public ModelAndView daftarSpop(@PageableDefault(size = 15)Pageable pageable){
        ModelAndView m = new ModelAndView();
        m.addObject("subTitle", "Daftar Semua SPOP");
        Page<Spop> page =  spopService.getAllSpop(pageable);
        m.addObject("page", page);
        m.setViewName("pages/admin/daftar-semua-spop");
        return  m;
    }

    @GetMapping("spop/{id}/update")
    public ModelAndView daftarSpop(@PathVariable("id") Long id, @RequestParam("statusSppt") boolean statusSppt){
        ModelAndView m = new ModelAndView();
        spopService.updateStatusSppt(id, statusSppt);
        m.setViewName("redirect:/admin/spop/daftar");
        return  m;
    }

    @GetMapping("spop/{id}/detail-sppt")
    public  ModelAndView detailSppt(@PathVariable("id") Long id) {
        ModelAndView m = new ModelAndView();
        Spop spop = spopService.getById(id);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        m.addObject("dateNow", dateFormat.format(date));
        m.addObject("subTitle", "Daftar Semua SPOP");
        m.addObject("spop", spop);
        m.addObject("totalPajak", spop.getNjopLuasBangunan() + spop.getNjopLuasTanah());
        m.setViewName("pages/admin/detail-sppt");
        return m;
    }

    @GetMapping("spop/{id}/detail-sppt/print")
    public  ModelAndView printSppt(@PathVariable("id") Long id) {
        ModelAndView m = new ModelAndView();
        Spop spop = spopService.getById(id);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        m.addObject("dateNow", dateFormat.format(date));
        m.addObject("spop", spop);
        m.addObject("totalPajak", spop.getNjopLuasBangunan() + spop.getNjopLuasTanah());
        m.setViewName("pages/admin/detail-sppt-print");
        return  m;
    }


}
