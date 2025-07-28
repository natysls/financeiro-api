package com.sop.naty.financeiro.controller;

import com.sop.naty.financeiro.dto.DespesaDTO;
import com.sop.naty.financeiro.service.DespesaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/despesa")
@RequiredArgsConstructor
@Tag(name = "Despesas", description = "Operações com Despesas")
public class DespesaController {
    private final DespesaService despesaService;

    @PostMapping
    public ResponseEntity<DespesaDTO> criar(@RequestBody DespesaDTO dto) {
        return ResponseEntity.ok(despesaService.criar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DespesaDTO> atualizar(@PathVariable Long id, @RequestBody DespesaDTO dto) {
        return ResponseEntity.ok(despesaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        despesaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DespesaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(despesaService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<DespesaDTO>> listarTodos() {
        return ResponseEntity.ok(despesaService.listarTodos());
    }
}
