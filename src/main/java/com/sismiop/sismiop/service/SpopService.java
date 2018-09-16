package com.sismiop.sismiop.service;

import com.sismiop.sismiop.model.PermohonanOp;
import com.sismiop.sismiop.model.Spop;
import com.sismiop.sismiop.repository.PermohonanOpRepository;
import com.sismiop.sismiop.repository.SpopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SpopService implements ModelService<Spop, Long>{
    @Autowired
    private SpopRepository spopRepository;

    @Autowired
    private PermohonanOpRepository permohonanOpRepository;

    @Override
    public boolean add(Spop spop) {
        try{
            spopRepository.save(spop);
            return  true;
        }catch (Exception e) {
            System.out.println("Error add(SpopService) "+e.getMessage());
        }
        return false;
    }

    @Override
    public boolean addAll(Iterable<Spop> spops) {
        try{
            spopRepository.saveAll(spops);
            return  true;
        }catch (Exception e) {
            System.out.println("Error addAll(SpopService) "+e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Spop spop) {
        Spop spopBaru = getById(spop.getId());
        spop.setPermohonanOp(spopBaru.getPermohonanOp());
        spop.setFileSuratKuasa(spopBaru.getFileSuratKuasa());
        spop.setFileBuktiSuratBangunan(spopBaru.getFileBuktiSuratBangunan());
        spop.setFileBuktiSuratTanah(spopBaru.getFileBuktiSuratTanah());
        spop.setFileIdentitasDiri(spopBaru.getFileIdentitasDiri());
        spop.setFileNpwp(spopBaru.getFileNpwp());
        return add(spop);
    }

    @Override
    public boolean delete(Long aLong) {
        try{
            if(spopRepository.existsById(aLong)){
                spopRepository.deleteById(aLong);
            }
            return  true;
        }catch (Exception e) {
            System.out.println("Error delete(SpopService)"+e.getMessage());
        }
        return false;
    }

    @Override
    public Spop getById(Long aLong) {
        return spopRepository.findSpopById(aLong);
    }

    public Spop getByPermohonanOpId(Long id) {
        Spop spop = null;
        spopRepository.findSpopByPermohonanOpId(id);
        return  spop;
    }

    public Page<Spop> getAllSpop(Pageable pageable) {
        return spopRepository.findAll(pageable);
    }

    public boolean updateStatusSppt(Long id, boolean status) {
        Spop spop = getById(id);

        if(status == true) {
            spop.setNjopLuasBangunan(spop.getPermohonanOp().getLuasBangunan()*1000);
            spop.setNjopLuasTanah(spop.getPermohonanOp().getLuasTanah()*900);
        }else {
            spop.setNjopLuasTanah(0);
            spop.setNjopLuasBangunan(0);
        }
        spop.setStatusSppt(status);
        return add(spop);
    }
}
