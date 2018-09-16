package com.sismiop.sismiop.service;

import com.sismiop.sismiop.model.Jabatan;
import com.sismiop.sismiop.model.JabatanPenduduk;
import com.sismiop.sismiop.model.Penduduk;
import com.sismiop.sismiop.repository.JabatanPendudukRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JabatanPendudukService implements ModelService<JabatanPenduduk, Long>{
    @Autowired
    private JabatanPendudukRepository jpr;

    @Override
    public boolean add(JabatanPenduduk jabatanPenduduk) {
        try{
            jpr.save(jabatanPenduduk);
            return true;
        }catch (Exception e){
            System.out.println("Error add(JabatanPenduduk). "+e.getMessage());
        }
        return false;
    }

    @Override
    public boolean addAll(Iterable<JabatanPenduduk> jabatanPenduduks) {
        try{
            jpr.saveAll(jabatanPenduduks);
            return true;
        }catch (Exception e){
            System.out.println("Error addAll(JabatanPenduduk). "+e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(JabatanPenduduk jabatanPenduduk) {
        try{
            add(jabatanPenduduk);
            return true;
        }catch (Exception e){
            System.out.println("Error update(JabatanPenduduk) "+e.getMessage() );
        }
        return false;
    }

    @Override
    public boolean delete(Long aLong) {
        try {
            JabatanPenduduk jp = jpr.findJabatanPendudukById(aLong);
            jpr.delete(jp);
            return true;
        }catch (Exception e){
            System.out.println("Error delete(JabatanPenduduk) "+e.getMessage());
        }
        return false;
    }

    @Override
    public JabatanPenduduk getById(Long aLong) {
        return jpr.findJabatanPendudukById(aLong);
    }

    public JabatanPenduduk getByPendudukIdAndJabatanId(Long pendudukId, Long jabatanId) {
        return jpr.findJabatanPendudukByPendudukIdAndJabatanId(pendudukId, jabatanId);
    }

    public JabatanPenduduk getJabatanPendudukByPendudukId(Long id) {
        return jpr.findJabatanPendudukByPendudukId(id);
    }


}
