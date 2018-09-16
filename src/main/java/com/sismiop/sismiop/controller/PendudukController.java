package com.sismiop.sismiop.controller;

import com.sismiop.sismiop.model.Penduduk;
import com.sismiop.sismiop.model.PermohonanOp;
import com.sismiop.sismiop.service.PendudukService;
import com.sismiop.sismiop.service.PermohonanOpService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import javax.validation.Valid;

@Controller
@ControllerAdvice
public class PendudukController {

    @Autowired
    private PendudukService pendudukService;

    @Autowired
    private PermohonanOpService permohonanOpService;


    @ModelAttribute
    public void addAttributes(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Penduduk penduduk = pendudukService.getPendudukByNoTelp(auth.getName());
        model.addAttribute("authUser", penduduk);
    }

    @GetMapping(value = {"/penduduk/register-spop"})
    public ModelAndView entrySPOP(Exception e) {
        ModelAndView m  = new ModelAndView();
        m.addObject("subTitle", "Registrasi SPOP Baru");
        m.setViewName("pages/penduduk/register-spop");
        return m;
    }

    @PostMapping(value = {"/penduduk/permohonan-op"})
    public ModelAndView savePermohonan(@Valid @ModelAttribute("newPermohonan") PermohonanOp permohonanOp, @Valid @ModelAttribute("authUser") Penduduk penduduk, BindingResult bindingResult) {
        ModelAndView m  = new ModelAndView();
        PermohonanOp op = new PermohonanOp();
        m.addObject("subTitle", "Registrasi Permohonan OP Baru");
        m.addObject("newPermohonan", op);
        permohonanOp.setPenduduk(penduduk);
        permohonanOp.setStatus(false);
        if(permohonanOpService.add(permohonanOp)) {
            m.addObject("success", true);
            m.setViewName("pages/penduduk/permohonan");
        }
        else {
            m.addObject("error", false);
            m.setViewName("pages/penduduk/permohonan");
        }
        return m;
    }

    @GetMapping(value = {"/penduduk/permohonan-op"})
    public ModelAndView createPermohonan(Exception e) {
        ModelAndView m  = new ModelAndView();
        PermohonanOp pop =  new PermohonanOp();
        m.addObject("subTitle", "Registrasi Permohonan OP");
        m.addObject("newPermohonan", pop);
        m.setViewName("pages/penduduk/permohonan");
        return m;
    }

    @PostMapping(value = {"/penduduk/permohonan-mutasi"})
    public ModelAndView savePermohonanMutasi(@Valid @ModelAttribute("pm") PermohonanOp permohonanMutasi, @Valid @ModelAttribute("authUser") Penduduk penduduk, BindingResult bindingResult) {
        ModelAndView m  = new ModelAndView();
        PermohonanOp pm = new PermohonanOp();
        m.addObject("subTitle", "Registrasi Permohonan Mutasi");
        m.addObject("newPermohonanMutasi", pm);
        permohonanMutasi.setPenduduk(penduduk);
        permohonanMutasi.setStatus(false);
        if(permohonanOpService.add(permohonanMutasi)) {
            m.addObject("success", true);
            m.setViewName("pages/penduduk/permohonan-mutasi");
        }
        else {
            m.addObject("error", false);
            m.setViewName("pages/penduduk/permohonan-mutasi");
        }
        return m;
    }

    @GetMapping(value = {"/penduduk/permohonan-mutasi"})
    public ModelAndView createPermohonanMutasi(Exception e) {
        ModelAndView m  = new ModelAndView();
        PermohonanOp pm =  new PermohonanOp();
        m.addObject("subTitle", "Registrasi Permohonan Mutasi");
        m.addObject("pm", pm);
        m.setViewName("pages/penduduk/permohonan-mutasi");
        return m;
    }

    @GetMapping("/penduduk/permohonan-op/daftar")
    public ModelAndView getAllDaftarPermohonanOp(@PageableDefault(size = 7)Pageable pageable, @Valid @ModelAttribute("authUser") Penduduk p) {
        ModelAndView m = new ModelAndView();
        m.addObject("subTitle", "Daftar Permohonan OP");
        Page<PermohonanOp> page =  permohonanOpService.getPageTablePermohonanByPendudukId(p.getId(), pageable);
        m.addObject("page", page);
        m.setViewName("pages/penduduk/daftar-permohonan-op");
        return  m;
    }



    @GetMapping("/penduduk/permohonan-op/delete/{id}")
    public ModelAndView deletePermohonanOp(@PathVariable ("id") Long id) {

        ModelAndView m = new ModelAndView();
        PermohonanOp pop = permohonanOpService.getById(id);
        if(permohonanOpService.deleteMyPermohonan(pop.getId())){
            System.out.println("===================================");
            System.out.println("Berhasil MenDelete + "+pop.getId());
            System.out.println("===================================");
        }
        else{
            System.out.println("Bukan pop penduduk milik anda");
        }
        m.setViewName("redirect:/penduduk/permohonan-op/daftar");
        return  m;
    }



    @GetMapping("/penduduk/permohonan-op/edit/{id}")
    public ModelAndView editPermohonanOp(@PathVariable ("id") Long id, @Valid @ModelAttribute("authUser") Penduduk p) {
        System.out.println("===================================");
        System.out.println("Edit");
        System.out.println("===================================");
        ModelAndView m = new ModelAndView();
        m.addObject("subTitle", "Edit Permohonan OP");
        PermohonanOp editPermohonan = permohonanOpService.getById(id);
        if(editPermohonan.getPenduduk().getId().equals(p.getId()) && editPermohonan.isStatus() == false) {
            m.addObject("editPermohonan", editPermohonan);
            m.setViewName("pages/penduduk/edit-permohonan");
        }else {
            m.setViewName("redirect:/403");
        }
        return  m;
    }

    @PostMapping({"/penduduk/permohonan-op/edit"})
    public ModelAndView saveEditPermohonan(PermohonanOp editPermohonan) {
        System.out.println("===================================");
        System.out.println("Edit Post");
        System.out.println("===================================");
        ModelAndView m  = new ModelAndView();
        if(editPermohonan.isStatus() == false) {
            if (permohonanOpService.update(editPermohonan)) {
                m.addObject("success", true);
                m.setViewName("redirect:/penduduk/permohonan-op/daftar");
            }
        }
        else {
            System.out.println("Permohonan telah di approve");
            m.addObject("error", true);
            m.setViewName("redirect:/penduduk/permohonan-op/daftar");
        }

        return m;
    }


}
