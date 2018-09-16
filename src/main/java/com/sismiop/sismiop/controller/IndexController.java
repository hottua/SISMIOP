package com.sismiop.sismiop.controller;

import com.sismiop.sismiop.model.Jabatan;
import com.sismiop.sismiop.model.JabatanPenduduk;
import com.sismiop.sismiop.model.Penduduk;
import com.sismiop.sismiop.repository.JabatanPendudukRepository;
import com.sismiop.sismiop.service.JabatanPendudukService;
import com.sismiop.sismiop.service.JabatanService;
import com.sismiop.sismiop.service.PendudukService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import javax.validation.Valid;

@Controller
public class IndexController {

    @Autowired
    private PendudukService pendudukService;

    @Autowired
    private JabatanService jabatanService;



    @Autowired
    private JabatanPendudukService jps;

    @GetMapping({"/dashboard", "/"})
    public ModelAndView home() {

        ModelAndView m  = new ModelAndView();
        m.addObject("subTitle", "Dashboard");
        m.setViewName("pages/dashboard");

        return m;
    }

    @RequestMapping("/login")
    public ModelAndView loginUser()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("subTitle", "Login");
        modelAndView.setViewName("login");
        return modelAndView;
    }

    // for 403 access denied page
    @GetMapping({"/403"})
    public ModelAndView accesssDenied() {
        ModelAndView model = new ModelAndView();
        model.addObject("subTitle", "Akses ditolak");
        model.setViewName("403");
        return model;

    }

    @GetMapping({"/404", "/error"})
    public ModelAndView pageNotFound() {

        ModelAndView model = new ModelAndView();
        model.addObject("subTitle", "Halaman tidak ditemukan");
        model.setViewName("404");
        return model;

    }

    @GetMapping("/register")
    public  ModelAndView registerPenduduk() {
        ModelAndView m = new ModelAndView();
        Penduduk p = new Penduduk();
        m.addObject("penduduk", p);
        m.setViewName("pages/penduduk/register-penduduk");
        return m;
    }

    @PostMapping("/register")
    public  ModelAndView savePendudukToDatabase(@Valid Penduduk penduduk, BindingResult bindingResult) {
        ModelAndView m  = new ModelAndView();
        Penduduk pendudukExists = pendudukService.getPendudukByNoTelp(penduduk.getNoTelp());
        if(pendudukExists != null) {
            bindingResult
                    .rejectValue("no_telp", "error.penduduk",
                            "Nomor telepon telah terdaftar");
        }
        if (bindingResult.hasErrors()) {
            m.setViewName("pages/penduduk/register-penduduk");
        } else {
            Jabatan j = jabatanService.getByRole("PENDUDUK");
            if(pendudukService.add(penduduk) && jps.add(new JabatanPenduduk(penduduk, j))) {
                m.addObject("successMessage", "Penduduk telah berhasil disimpan. Silahkan login");
                m.addObject("penduduk", new Penduduk());
                m.setViewName("redirect:/login");
            }else {
                m.addObject("errorMessage", "Penduduk tidak berhasil disimpan.");
                m.addObject("penduduk", new Penduduk());
                m.setViewName("pages/penduduk/register-penduduk");
            }

        }
        return  m;
    }


}
