package com.sop.naty.financeiro.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "empenhos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Empenho {
    // Garante o comprometimento do governo em excecutar a dívida referente as despesas.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "num_empenho", nullable = false, unique = true)
    private String numeroEmpenho; //"anoAtual" + "NE" + "sequencial de 4 dígitos"

    @Column(name = "data_empenho", nullable = false)
    private LocalDate dataEmpenho;

    @Column(name = "valor", nullable = false)
    private BigDecimal valor;

    @Column(name = "observacao")
    private String observacao;

    // Obrigatoriamente pertencente à uma Despesa, e apenas uma.
    @ManyToOne
    @JoinColumn(name = "fk_despesa", nullable = false)
    private Despesa despesa;

    // Pode conter nenhum ou vários Pagamentos.
    @OneToMany(mappedBy = "empenho", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pagamento> pagamentos;

}
