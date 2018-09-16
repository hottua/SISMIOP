package com.sismiop.sismiop.repository;

import com.sismiop.sismiop.model.Jabatan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface JabatanRepository extends JpaRepository<Jabatan, Long> {
    Jabatan findJabatanById(Long id);
    Jabatan findJabatanByRole(String role);
}
