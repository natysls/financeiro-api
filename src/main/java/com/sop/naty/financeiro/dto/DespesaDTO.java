package com.sop.naty.financeiro.dto;

import com.sop.naty.financeiro.enumeration.Status;
import com.sop.naty.financeiro.enumeration.TipoDespesa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DespesaDTO {

    private Long id;
    private String numeroProtocolo;
    private TipoDespesa tipoDespesa;
    private LocalDateTime dataProtocolo;
    private LocalDate dataVencimento;
    private String credor;
    private String descricao;
    private BigDecimal valor;
    private Status status;
}
