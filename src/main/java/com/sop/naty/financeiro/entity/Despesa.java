package com.sop.naty.financeiro.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "despesa")
@Data // do Lombok (getters, setters, toString)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Despesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Cada número de protocolo deve ser único.
    @Column(name = "num_protocolo", nullable = false, unique = true)
    private String numeroProtocolo;

    @Column(name = "tipo_despesa", nullable = false)
    private String tipoDespesa;

    @Column(name = "data_protocolo", nullable = false)
    private LocalDateTime dataProtocolo;

    @Column(name = "data_vencimento", nullable = false)
    private LocalDate dataVencimento;

    @Column(name = "credor", nullable = false)
    private String credor;

    @Column(name = "descricao", columnDefinition = "TEXT", nullable = false)
    private String descricao;

    @Column(name = "valor", nullable = false)
    private BigDecimal valor;

    @Column(name = "status")
    private String status;

    // Pode conter nenhum ou vários Empenhos.
    // Se um Empenho for removido da lista, ele é apagado automaticamente.
    @OneToMany(mappedBy = "despesa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Empenho> empenhos;

}
