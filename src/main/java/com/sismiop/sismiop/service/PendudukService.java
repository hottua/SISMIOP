package com.sismiop.sismiop.service;

import com.sismiop.sismiop.model.Jabatan;
import com.sismiop.sismiop.model.Penduduk;
import com.sismiop.sismiop.repository.JabatanPendudukRepository;
import com.sismiop.sismiop.repository.PendudukRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PendudukService implements ModelService<Penduduk, Long>, UserDetailsService {

    @Autowired
    private PendudukRepository pendudukRepository;

    @Autowired
    private JabatanPendudukRepository jpr;

    @Autowired
    private BCryptPasswordEncoder  bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Penduduk p = getPendudukByNoTelp(s);

        User.UserBuilder userBuilder = null;
        if(p != null) {
            userBuilder = User.withUsername(s);
            userBuilder.password(p.getPassword());
            Set<String> roles =  new HashSet<>();
            p.getJabatanPenduduks().forEach(jp -> {
                roles.add(jp.getJabatan().getRole());
            });
            userBuilder.roles(roles.toArray(new String[roles.size()]));
        }
        return userBuilder.build();
    }

    @Override
    public boolean add(Penduduk p) {
        try {
            p.setPassword(bCryptPasswordEncoder.encode(p.getPassword()));
            pendudukRepository.save(p);
            return true;
        }catch (Exception e) {
            System.out.println("Error add(PendudukService) "+e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Penduduk p) {
        try {
            if(pendudukRepository.existsById(p.getId())) {
                pendudukRepository.save(p);
                return true;
            }else {
                System.out.println("User not found (Update Penduduk)");
            }
        }catch (Exception e) {
            System.out.println("Error update (Penduduk) "+e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        try {
            pendudukRepository.delete(pendudukRepository.findPendudukById(id));
            return true;
        }catch (Exception e) {
            System.out.println("Error delete(Penduduk) "+e.getMessage());
        }
        return false;
    }

    @Override
    public Penduduk getById(Long aLong) {
        return pendudukRepository.findPendudukById(aLong);
    }

    @Override
    public boolean addAll(Iterable<Penduduk> penduduks) {
        try {
            pendudukRepository.saveAll(penduduks);
            return true;
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Page<Penduduk> getAllPenduduk(Pageable pageable) {
        return pendudukRepository.findAll(pageable);
    }

    public Penduduk getPendudukById(Long id) {
        return pendudukRepository.findPendudukById(id);
    }

    public Penduduk getPendudukByNIK(String NIK) {
        return pendudukRepository.findPendudukByNik(NIK);
    }

    public Penduduk getPendudukByNoTelp(String noTelp) {
        return pendudukRepository.findPendudukByNoTelp(noTelp);
    }

    public Page<Penduduk> getPendudukByNama(String nama, Pageable pageable) {
        return pendudukRepository.findPendudukByNama(nama, pageable);
    }

    public boolean deletePenduduk(Long id) {
        try {
//            jpr.deleteJabatanPendudukByPendudukId(id);
            pendudukRepository.delete(pendudukRepository.findPendudukById(id));
            return true;
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean updatePassword(Long id, String newPassword) {
        try{
            Penduduk p = pendudukRepository.findPendudukById(id);
            p.setPassword(bCryptPasswordEncoder.encode(newPassword));
            pendudukRepository.save(p);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

}
