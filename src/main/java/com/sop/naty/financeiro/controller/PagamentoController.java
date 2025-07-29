package com.sop.naty.financeiro.controller;

import com.sop.naty.financeiro.dto.PagamentoDTO;
import com.sop.naty.financeiro.entity.Empenho;
import com.sop.naty.financeiro.entity.Pagamento;
import com.sop.naty.financeiro.repository.EmpenhoRepository;
import com.sop.naty.financeiro.repository.PagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pagamento")
@RequiredArgsConstructor
public class PagamentoController {

    private final PagamentoRepository pagamentoRepository;
    private final EmpenhoRepository empenhoRepository;

    public PagamentoDTO criar(PagamentoDTO dto) {
        if (pagamentoRepository.existsByNumeroPagamento(dto.getNumeroPagamento())) {
            throw new RuntimeException("Número de pagamento já existe");
        }

        Empenho empenho = empenhoRepository.findById(dto.getFkEmpenho())
                .orElseThrow(() -> new RuntimeException("Empenho não encontrado"));

        var somaAtual = empenho.getPagamentos().stream()
                .map(Pagamento::getValor)
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);

        if (somaAtual.add(dto.getValor()).compareTo(empenho.getValor()) > 0) {
            throw new RuntimeException("Valor do pagamento excede o valor disponível do empenho");
        }

        Pagamento pagamento = fromDTO(dto);
        pagamento.setEmpenho(empenho);

        return toDTO(pagamentoRepository.save(pagamento));
    }

    public PagamentoDTO atualizar(Long id, PagamentoDTO dto) {
        Pagamento existente = pagamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));

        existente.setNumeroPagamento(dto.getNumeroPagamento());
        existente.setDataPagamento(dto.getDataPagamento());
        existente.setValor(dto.getValor());
        existente.setObservacao(dto.getObservacao());

        return toDTO(pagamentoRepository.save(existente));
    }

    public void deletar(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));

        pagamentoRepository.delete(pagamento);
    }

    public PagamentoDTO buscarPorId(Long id) {
        return pagamentoRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
    }

    public List<PagamentoDTO> listarTodos() {
        return pagamentoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private PagamentoDTO toDTO(Pagamento pagamento) {
        return PagamentoDTO.builder()
                .id(pagamento.getId())
                .numeroPagamento(pagamento.getNumeroPagamento())
                .dataPagamento(pagamento.getDataPagamento())
                .valor(pagamento.getValor())
                .observacao(pagamento.getObservacao())
                .fkEmpenho(pagamento.getEmpenho().getId())
                .build();
    }

    private Pagamento fromDTO(PagamentoDTO dto) {
        return Pagamento.builder()
                .numeroPagamento(dto.getNumeroPagamento())
                .dataPagamento(dto.getDataPagamento())
                .valor(dto.getValor())
                .observacao(dto.getObservacao())
                .build();
    }
}
