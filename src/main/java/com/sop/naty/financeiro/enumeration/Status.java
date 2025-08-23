package com.sop.naty.financeiro.enumeration;

import lombok.Getter;

@Getter
public enum Status {
    AGUARDANDO_EMPENHO("Aguardando Empenho"),
    PARCIALMENTE_EMPENHADA("Parcialmente Empenhada"),
    AGUARDANDO_PAGAMENTO("Aguardando Pagamento"),
    PARCIALMENTE_PAGA("Parcialmente Paga"),
    PAGA("Paga");

    private final String descricao;

    Status(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
