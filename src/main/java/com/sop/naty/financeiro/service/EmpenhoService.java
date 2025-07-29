package com.sop.naty.financeiro.service;

import com.sop.naty.financeiro.dto.EmpenhoDTO;
import com.sop.naty.financeiro.entity.Despesa;
import com.sop.naty.financeiro.entity.Empenho;
import com.sop.naty.financeiro.repository.DespesaRepository;
import com.sop.naty.financeiro.repository.EmpenhoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpenhoService {

    private final EmpenhoRepository empenhoRepository;
    private final DespesaRepository despesaRepository;

    public EmpenhoDTO criar(EmpenhoDTO dto) {
        if (empenhoRepository.existsByNumeroEmpenho(dto.getNumeroEmpenho())) {
            throw new RuntimeException("Número de empenho já existe");
        }

        Despesa despesa = despesaRepository.findById(dto.getFkDespesa())
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada"));

        // Verificar limite do valor
        var somaAtual = despesa.getEmpenhos().stream()
                .map(Empenho::getValor)
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);

        if (somaAtual.add(dto.getValor()).compareTo(despesa.getValor()) > 0) {
            throw new RuntimeException("Valor do empenho excede o valor disponível da despesa");
        }

        Empenho empenho = fromDTO(dto);
        empenho.setDespesa(despesa);

        return toDTO(empenhoRepository.save(empenho));
    }

    public EmpenhoDTO atualizar(Long id, EmpenhoDTO dto) {
        Empenho existente = empenhoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empenho não encontrado"));

        existente.setNumeroEmpenho(dto.getNumeroEmpenho());
        existente.setDataEmpenho(dto.getDataEmpenho());
        existente.setValor(dto.getValor());
        existente.setObservacao(dto.getObservacao());

        return toDTO(empenhoRepository.save(existente));
    }

    public void deletar(Long id) {
        Empenho empenho = empenhoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empenho não encontrado"));

        if (empenho.getPagamentos() != null && !empenho.getPagamentos().isEmpty()) {
            throw new RuntimeException("Não é possível excluir um empenho com pagamentos vinculados");
        }

        empenhoRepository.delete(empenho);
    }

    public EmpenhoDTO buscarPorId(Long id) {
        return empenhoRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Empenho não encontrado"));
    }

    public List<EmpenhoDTO> listarTodos() {
        return empenhoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private EmpenhoDTO toDTO(Empenho empenho) {
        return EmpenhoDTO.builder()
                .id(empenho.getId())
                .numeroEmpenho(empenho.getNumeroEmpenho())
                .dataEmpenho(empenho.getDataEmpenho())
                .valor(empenho.getValor())
                .observacao(empenho.getObservacao())
                .fkDespesa(empenho.getDespesa().getId())
                .build();
    }

    private Empenho fromDTO(EmpenhoDTO dto) {
        return Empenho.builder()
                .numeroEmpenho(dto.getNumeroEmpenho())
                .dataEmpenho(dto.getDataEmpenho())
                .valor(dto.getValor())
                .observacao(dto.getObservacao())
                .build();
    }
}
