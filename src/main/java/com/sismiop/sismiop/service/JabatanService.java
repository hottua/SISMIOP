package com.sismiop.sismiop.service;

import com.sismiop.sismiop.model.Jabatan;
import com.sismiop.sismiop.repository.JabatanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class JabatanService implements ModelService<Jabatan, Long>{

    @Autowired
    private JabatanRepository jabatanRepository;

    @Override
    public boolean add(Jabatan j) {
        try {
            jabatanRepository.save(j);
            return true;
        }catch (Exception e) {
            System.out.println("Error add(Jabatan) "+e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Jabatan j) {
        try{
            Jabatan jabatanUpdate = jabatanRepository.findJabatanById(j.getId());
            jabatanUpdate.setKeterangan(j.getKeterangan());
            jabatanUpdate.setRole(j.getRole());
            return true;
        }catch (Exception e) {
            System.out.println("Error update(JabatanService) "+e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        try {
            jabatanRepository.delete(jabatanRepository.findJabatanById(id));
            return true;
        }catch (Exception e) {
            System.out.println("Error delete(JabatanService) " +e.getMessage());
        }
        return false;
    }

    @Override
    public Jabatan getById(Long aLong) {
        return jabatanRepository.findJabatanById(aLong);
    }

    @Override
    public boolean addAll(Iterable<Jabatan> jabatans) {
        try {
            jabatanRepository.saveAll(jabatans);
            return true;
        }catch (Exception e) {
            System.out.println("Error addAll(JabatanService) "+e.getMessage());
        }
        return false;
    }

    public Jabatan getByRole(String role) {
        return jabatanRepository.findJabatanByRole(role);
    }

    public Page<Jabatan> getAllJabatan(Pageable pageable) {
        return jabatanRepository.findAll(pageable);
    }

}
