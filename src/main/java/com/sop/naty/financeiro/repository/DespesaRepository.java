package com.sop.naty.financeiro.repository;

import com.sop.naty.financeiro.entity.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {
    Optional<Despesa> findByNumProtocolo(String numeroProtocolo);
    boolean existsByNumProtocolo(String numeroProtocolo);
}
