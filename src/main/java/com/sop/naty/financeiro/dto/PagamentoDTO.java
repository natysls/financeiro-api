package com.sop.naty.financeiro.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagamentoDTO {

    private Long id;
    private String numeroPagamento;
    private LocalDate dataPagamento;
    private BigDecimal valor;
    private String observacao;
    private Long fkEmpenho;
}
