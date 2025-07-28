package com.sop.naty.financeiro.repository;

import com.sop.naty.financeiro.entity.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    Optional<Pagamento> findByNumPagamento(String numeroPagamento);
    boolean existsByNumPagamento(String numeroPagamento);
}
