package com.sismiop.sismiop.repository;

import com.sismiop.sismiop.model.Spop;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;

@Repository
public interface SpopRepository extends JpaRepository<Spop, Long> {
    Spop findSpopById(Long id);
    Spop findSpopByPermohonanOpId(Long id);

}
