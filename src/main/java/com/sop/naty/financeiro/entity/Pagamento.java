package com.sop.naty.financeiro.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "pagamentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "num_pagamento", nullable = false, unique = true)
    private String numeroPagamento;

    @Column(name = "data_pagamento", nullable = false)
    private LocalDate dataPagamento;

    @Column(name = "valor", nullable = false)
    private BigDecimal valor;

    @Column(name = "observacao")
    private String observacao;

    //Obrigatoriamente pertencente à um Empenho, e apenas um.
    @ManyToOne
    @JoinColumn(name = "empenho_id", nullable = false)
    private Empenho empenho;
}
