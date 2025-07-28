package com.sop.naty.financeiro.repository;

import com.sop.naty.financeiro.entity.Empenho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpenhoRepository extends JpaRepository<Empenho, Long> {

    Optional<Empenho> findByNumEmpenho(String numeroEmpenho);
    boolean existsByNumEmpenho(String numeroEmpenho);
}
