package TopReports.Dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AcumuladoDTO {



    private Long id;
    private String venda;
    private String tipo;
    private BigDecimal  valor;
    private LocalDate data;

    public AcumuladoDTO(Long id , String venda, String tipo, BigDecimal valor, LocalDate data) {
        this.id = id;
        this.venda = venda;
        this.tipo = tipo;
        this.valor = valor;
        this.data = data;

    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVenda() {
        return venda;
    }

    public void setVenda(String venda) {
        this.venda = venda;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
