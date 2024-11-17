package Topmix.reports.Dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RelatorioDTO {

    private String produto;
    private Integer quantidade;
    private BigDecimal valor;
    private LocalDate data;

    // Construtor
    public RelatorioDTO(String produto, Integer quantidade, BigDecimal valor, LocalDate data) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.valor = valor;
        this.data = data;
    }

    // Getters e Setters
    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
