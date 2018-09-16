package com.sismiop.sismiop.service;

import com.sismiop.sismiop.model.Penduduk;
import com.sismiop.sismiop.model.PermohonanOp;
import com.sismiop.sismiop.model.Spop;
import com.sismiop.sismiop.repository.PermohonanOpRepository;
import com.sismiop.sismiop.repository.SpopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Executable;
import java.util.List;

@Service
public class PermohonanOpService implements  ModelService<PermohonanOp, Long>{
    @Autowired
    private PermohonanOpRepository permohonanOpRepository;

    @Autowired
    private SpopRepository spopRepository;

    @Autowired
    private PendudukService pendudukService;

    @Override
    public boolean add(PermohonanOp permohonanOp) {
        try{
            permohonanOpRepository.save(permohonanOp);
            return true;
        }catch (Exception e) {
            System.out.println("Error add(PermohonanOP) "+e.getMessage());
        }
        return false;
    }

    @Override
    public boolean addAll(Iterable<PermohonanOp> permohonanOps) {
        try{
            permohonanOpRepository.saveAll(permohonanOps);
            return true;
        }catch (Exception e) {
            System.out.println("Error addAll(PermohonanOp) "+e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(PermohonanOp permohonanOp) {
        try{
            if (permohonanOpRepository.existsById(permohonanOp.getId())) {
                PermohonanOp permohonanOpUpdated = permohonanOpRepository.findPermohonanOpById(permohonanOp.getId());
                permohonanOpUpdated.setLuasTanah(permohonanOp.getLuasTanah());
                permohonanOpUpdated.setLuasBangunan(permohonanOp.getLuasBangunan());
                permohonanOpUpdated.setLetakObjek(permohonanOp.getLetakObjek());
                permohonanOpUpdated.setKelurahan(permohonanOp.getKelurahan());
                permohonanOpUpdated.setKecamatan(permohonanOp.getKecamatan());
                permohonanOpUpdated.setBertindakSebagai(permohonanOp.getBertindakSebagai());
                permohonanOpRepository.save(permohonanOpUpdated);
                return true;
            }else{
                System.out.println("PermohonanOp "+permohonanOp.getId()+" not found");
            }
        }catch (Exception e) {
            System.out.println("Error update(PermohonanOp) "+e.getMessage());
        }

        return false;
    }

    public boolean updateStatus(Long id, boolean status) {
        try{
            if (permohonanOpRepository.existsById(id)) {
                PermohonanOp permohonanOpUpdated = permohonanOpRepository.findPermohonanOpById(id);
                permohonanOpUpdated.setStatus(status);
                permohonanOpRepository.save(permohonanOpUpdated);
                return true;
            }else{
                System.out.println("PermohonanOp "+id+" not found");
            }
        }catch (Exception e) {
            System.out.println("Error updateStataus(PermohonanOp) "+e.getMessage());
        }

        return false;
    }

    @Override
    public boolean delete(Long aLong) {
        try{
            if(permohonanOpRepository.existsById(aLong)) {
                permohonanOpRepository.deleteById(aLong);
                return true;
            }else {
                System.out.println("PermohonanOp "+aLong+" Not Found");
            }
        }catch (Exception e) {
            System.out.println("Error delete(PermohonanOpService) "+e.getMessage());
        }
        return false;
    }

    public boolean deleteMyPermohonan(Long permohonanId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Penduduk penduduk = pendudukService.getPendudukByNoTelp(auth.getName());
        PermohonanOp pop = getById(permohonanId);
        if(pop.getPenduduk().equals(penduduk)){
            return delete(pop.getId());
        }
        return false;
    }

    @Override
    public PermohonanOp getById(Long aLong) {
        return permohonanOpRepository.findPermohonanOpById(aLong);
    }

    public Page<PermohonanOp> getAllPermohonan(Pageable pageable){
        return permohonanOpRepository.findAll(pageable);
    }


    public Page<PermohonanOp> getPageTablePermohonanByPendudukId(Long id, Pageable pageable) {
        return permohonanOpRepository.findPermohonanOpByPendudukId(id, pageable);
    }

    public List<PermohonanOp> getAllPermohonanByPendudukId(Long id){
        return permohonanOpRepository.findPermohonanOpByPendudukId(id);
    }
}
