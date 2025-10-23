package com.sop.naty.financeiro.controller;

import com.sop.naty.financeiro.enumeration.RoleUsuario;
import com.sop.naty.financeiro.enumeration.Status;
import com.sop.naty.financeiro.record.EnumRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/roles-usuario")
public class RoleUsuarioController {
    @GetMapping
    public ResponseEntity<List<EnumRecord>> listarTipos() {
        List<EnumRecord> tipos = Arrays.stream(RoleUsuario.values())
                .map(t -> new EnumRecord(t.name(), t.getDescricao()))
                .toList();
        return ResponseEntity.ok(tipos);
    }
}
