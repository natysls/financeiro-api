package com.sop.naty.financeiro.repository;

import com.sop.naty.financeiro.entity.Empenho;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpenhoRepository extends JpaRepository<Empenho, Long> {
}
