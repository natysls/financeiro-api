package com.sop.naty.financeiro.enumeration;

import lombok.Getter;

@Getter
public enum RoleUsuario {
    ROLE_USER("Role de Usuario"),
    ROLE_ADMIN("Role de Administrador");

    private final String descricao;

    RoleUsuario(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
