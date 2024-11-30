package TopmixReports.Enity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "acumulados")
public class Acumulado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @NotNull(message = "O campo 'venda' não pode ser nulo.")
    @Size(min = 3, max = 100, message = "O campo 'nome' deve ter entre 3 e 100 caracteres.")

    private String venda;
    private String tipo;

    @NotNull(message = "O campo 'valor' não pode ser nulo.")
    @DecimalMin(value = "0.0", inclusive = false, message = "O campo 'valor' deve ser maior que zero.")
    private BigDecimal valor;
    private LocalDate data;

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
