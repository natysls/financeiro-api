package com.sop.naty.financeiro.service;

import com.sop.naty.financeiro.dto.DespesaDTO;
import com.sop.naty.financeiro.entity.Despesa;
import com.sop.naty.financeiro.repository.DespesaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DespesaService {
    private final DespesaRepository despesaRepository;

    public DespesaDTO criar(DespesaDTO dto) {
        if (despesaRepository.existsByNumeroProtocolo(dto.getNumeroProtocolo())) {
            throw new RuntimeException("Número de protocolo já existe");
        }
        Despesa despesa = fromDTO(dto);
        return toDTO(despesaRepository.save(despesa));
    }

    public DespesaDTO atualizar(Long id, DespesaDTO dto) {
        Despesa existente = despesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada"));

        existente.setTipoDespesa(dto.getTipoDespesa());
        existente.setDataProtocolo(dto.getDataProtocolo());
        existente.setDataVencimento(dto.getDataVencimento());
        existente.setCredor(dto.getCredor());
        existente.setDescricao(dto.getDescricao());
        existente.setValor(dto.getValor());
        existente.setStatus(dto.getStatus());

        return toDTO(despesaRepository.save(existente));
    }

    public void deletar(Long id) {
        Despesa despesa = despesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada"));

        if (despesa.getEmpenhos() != null && !despesa.getEmpenhos().isEmpty()) {
            throw new RuntimeException("Não é possível excluir uma despesa com empenhos vinculados");
        }

        despesaRepository.delete(despesa);
    }

    public DespesaDTO buscarPorId(Long id) {
        return despesaRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada"));
    }

    public List<DespesaDTO> listarTodos() {
        return despesaRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Conversão manual DTO ↔ Entidade
    private DespesaDTO toDTO(Despesa despesa) {
        return DespesaDTO.builder()
                .id(despesa.getId())
                .numeroProtocolo(despesa.getNumeroProtocolo())
                .tipoDespesa(despesa.getTipoDespesa())
                .dataProtocolo(despesa.getDataProtocolo())
                .dataVencimento(despesa.getDataVencimento())
                .credor(despesa.getCredor())
                .descricao(despesa.getDescricao())
                .valor(despesa.getValor())
                .status(despesa.getStatus())
                .build();
    }

    private Despesa fromDTO(DespesaDTO dto) {
        return Despesa.builder()
                .numeroProtocolo(dto.getNumeroProtocolo())
                .tipoDespesa(dto.getTipoDespesa())
                .dataProtocolo(dto.getDataProtocolo())
                .dataVencimento(dto.getDataVencimento())
                .credor(dto.getCredor())
                .descricao(dto.getDescricao())
                .valor(dto.getValor())
                .status(dto.getStatus())
                .build();
    }
}
