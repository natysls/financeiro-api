package com.sop.naty.financeiro.controller;

import com.sop.naty.financeiro.dto.EmpenhoDTO;
import com.sop.naty.financeiro.service.EmpenhoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empenho")
@RequiredArgsConstructor
@Tag(name = "Empenhos", description = "Operações com Empenhos")
public class EmpenhoController {

    private final EmpenhoService empenhoService;

    @PostMapping
    public ResponseEntity<EmpenhoDTO> criar(@RequestBody EmpenhoDTO dto) {
        return ResponseEntity.ok(empenhoService.criar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpenhoDTO> atualizar(@PathVariable Long id, @RequestBody EmpenhoDTO dto) {
        return ResponseEntity.ok(empenhoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        empenhoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpenhoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(empenhoService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<EmpenhoDTO>> listarTodos() {
        return ResponseEntity.ok(empenhoService.listarTodos());
    }
}
