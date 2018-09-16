package com.sismiop.sismiop.repository;

import com.sismiop.sismiop.model.Penduduk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface PendudukRepository extends JpaRepository<Penduduk, Long> {
    Penduduk findPendudukById(Long id);
    Penduduk findPendudukByNik(String nik);
    Penduduk findPendudukByNoTelp(String noTelp);
    Page<Penduduk> findPendudukByNama(String nama, Pageable pageable);

}
