package com.sismiop.sismiop.controller;

import com.sismiop.sismiop.model.Penduduk;
import com.sismiop.sismiop.model.PermohonanOp;
import com.sismiop.sismiop.model.Spop;
import com.sismiop.sismiop.service.FileStorageService;
import com.sismiop.sismiop.service.PendudukService;
import com.sismiop.sismiop.service.PermohonanOpService;
import com.sismiop.sismiop.service.SpopService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.logging.Logger;

@Controller
public class SpopController {

    @Autowired
    private PermohonanOpService permohonanOpService;
    @Autowired
    private SpopService spopService;

    @Autowired
    private PendudukService pendudukService;


    @GetMapping({"/penduduk/permohonan-op/edit/{id}/register-spop"})
    public ModelAndView editPermohonanOp(@PathVariable("id") Long id) {
        ModelAndView m = new ModelAndView();
        m.addObject("subTitle", "Register SPOP");
        PermohonanOp permohonanOp = permohonanOpService.getById(id);
        if(permohonanOp.isStatus() && spopService.getByPermohonanOpId(permohonanOp.getId())== null) {
            Spop spop = new Spop();
            spop.setPermohonanOp(permohonanOp);
            m.addObject("spopBaru", spop);
            m.setViewName("pages/penduduk/register-spop");
        }else {
            m.setViewName("redirect:/403");
        }
        return  m;
    }



    @PostMapping({"/penduduk/permohonan-op/edit/{id}/register-spop"})
    public ModelAndView simpanEditPermohonanOp(@PathVariable("id") Long id,
                                               @Valid @ModelAttribute("spopBaru") Spop spopBaru,
                                               @Valid @ModelAttribute("fileIdentitasDiriM")MultipartFile fileIdentitasDiriM,
                                               @Valid @ModelAttribute("fileBuktiSuratTanahM")MultipartFile fileBuktiSuratTanahM,
                                               @Valid @ModelAttribute("fileBuktiSuratBangunanM")MultipartFile fileBuktiSuratBangunanM,
                                               @Valid @ModelAttribute("fileNpwpM") MultipartFile fileNpwpM,
                                               @Valid @ModelAttribute("fileSuratKuasaM") MultipartFile fileSuratKuasaM) {
        ModelAndView m = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        FileStorageService fileStorageService = new FileStorageService();
        fileStorageService.setFileStorageLocation(auth.getName()+"/"+id );
        spopBaru.setFileIdentitasDiri("/"+auth.getName()+"/"+id+"/"+fileStorageService.storeFile(fileIdentitasDiriM));
        spopBaru.setFileBuktiSuratTanah("/"+auth.getName()+"/"+id+"/"+fileStorageService.storeFile(fileBuktiSuratTanahM));
        spopBaru.setFileBuktiSuratBangunan("/"+auth.getName()+"/"+id+"/"+fileStorageService.storeFile(fileBuktiSuratBangunanM));
        spopBaru.setFileNpwp("/"+auth.getName()+"/"+id+"/"+fileStorageService.storeFile(fileNpwpM));
        spopBaru.setFileSuratKuasa("/"+auth.getName()+"/"+id+"/"+fileStorageService.storeFile(fileSuratKuasaM));
        if(id.equals(spopBaru.getId()) && spopService.add(spopBaru)) {
            m.addObject("msg", "sukses");
        }else
            m.addObject("msg", "gagal");
        m.setViewName("redirect:/penduduk/permohonan-op/daftar");
        return m;
    }

    @GetMapping("/file/downloadFile/{location}/{id}/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, @PathVariable String location, @PathVariable String id, HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        FileStorageService fileStorageService = new FileStorageService();
        fileStorageService.setFileStorageLocation(auth.getName()+"/"+id);
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        }catch (IOException ex) {
            System.out.println("Could not determine file type.");
        }

        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename\"" + resource.getFilename() +"\"")
                .body(resource);
    }

    @GetMapping({"/penduduk/permohonan-op/{id}/detail-spop"})
    public ModelAndView detailSpop(@PathVariable("id") Long id,
                                   @RequestParam(value = "edit", required = false, defaultValue = "false") boolean edit) {
        ModelAndView m = new ModelAndView();
        m.addObject("subTitle", "Detail SPOP");
        m.addObject("edit", edit);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Penduduk penduduk = pendudukService.getPendudukByNoTelp(auth.getName());
        PermohonanOp permohonanOp = permohonanOpService.getById(id);

        if(permohonanOp.getPenduduk().equals(penduduk) || penduduk.getJabatanPenduduks().toString().contains("ADMIN")) {
            if(permohonanOp.getPenduduk().equals(penduduk)) {
                m.addObject("canEdit", true);
            }
            m.addObject("spopBaru", permohonanOp.getSpop());
            m.setViewName("pages/penduduk/detail-spop");
        }else {
            m.setViewName("redirect:/403");
        }
        return  m;
    }

    @PostMapping({"/penduduk/permohonan-op/{id}/detail-spop"})
    public ModelAndView saveEditSpop(Spop spopBaru, @PathVariable("id") Long id) {
        ModelAndView m = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Penduduk penduduk = pendudukService.getPendudukByNoTelp(auth.getName());
        if(spopBaru.getPermohonanOp().getPenduduk().getId().equals(penduduk.getId())) {
            if (spopService.update(spopBaru)) {
                m.setViewName("redirect:/penduduk/permohonan-op/" + id + "/detail-spop");
            } else {
                m.setViewName("redirect:/penduduk/permohonan-op/" + id + "/detail-spop?edit=true");
            }
        } else {
            m.setViewName("redirect:/403");
        }
        return  m;
    }
}

