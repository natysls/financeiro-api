package com.sop.naty.financeiro.enumeration;

import lombok.Getter;

@Getter
public enum TipoDespesa {
    OBRA_DE_EDIFICACAO("Obra de Edificação"),
    OBRA_DE_RODOVIAS("Obra de Rodovias"),
    OUTROS("Outros");

    private final String descricao;

    TipoDespesa(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
