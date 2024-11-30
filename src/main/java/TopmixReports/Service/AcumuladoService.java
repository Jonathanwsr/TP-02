package TopmixReports.Service;



import TopmixReports.Dto.AcumuladoDTO;
import TopmixReports.Enity.Acumulado;
import TopmixReports.Repository.AcumuladoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

@Service
public class AcumuladoService {


    private final AcumuladoRepository acumuladoRepository;

    public AcumuladoService(AcumuladoRepository acumuladoRepository) {
        this.acumuladoRepository = acumuladoRepository;
    }

    public List<AcumuladoDTO> buscarVendasDiarias() {
        LocalDate hoje = LocalDate.now();
        return acumuladoRepository.findAllByData(hoje);
    }

    public List<AcumuladoDTO> buscarVendasSemanais() {
        LocalDate hoje = LocalDate.now();
        LocalDate inicioSemana = hoje.minusDays(hoje.getDayOfWeek().getValue() - 1);
        LocalDate fimSemana = inicioSemana.plusDays(6);
        return acumuladoRepository.findAllByDataBetween(inicioSemana, fimSemana);
    }

    public List<AcumuladoDTO> buscarVendasMensais() {
        LocalDate hoje = LocalDate.now();
        LocalDate inicioMes = hoje.withDayOfMonth(1);
        LocalDate fimMes = hoje.withDayOfMonth(hoje.lengthOfMonth());
        return acumuladoRepository.findAllByDataBetween(inicioMes, fimMes);
    }

    public List<AcumuladoDTO> buscarVendasPorPeriodo(LocalDate inicio, LocalDate fim) {
        return acumuladoRepository.findAllByDataBetween(inicio, fim);
    }

    public BigDecimal somarValorDasVendas(List<AcumuladoDTO> vendas) {
        return vendas.stream()
                .map(AcumuladoDTO::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public long contarQuantidadeDeVendas(List<AcumuladoDTO> vendas) {
        return vendas.size();
    }

    public Acumulado salvarAcumulado(Acumulado acumulado) {
        try {
            return acumuladoRepository.save(acumulado);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar o acumulado: " + e.getMessage(), e);
        }
    }

    public Acumulado atualizarParcial(Long id, AcumuladoDTO acumuladoParcial) {
        Acumulado acumulado = acumuladoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Acumulado com ID " + id + " não encontrado."));

        // Atualiza somente os campos que não são nulos
        if (acumuladoParcial.getVenda() != null) acumulado.setVenda(acumuladoParcial.getVenda());
        if (acumuladoParcial.getTipo() != null) acumulado.setTipo(acumuladoParcial.getTipo());
        if (acumuladoParcial.getValor() != null) acumulado.setValor(acumuladoParcial.getValor());
        if (acumuladoParcial.getData() != null) acumulado.setData(acumuladoParcial.getData());

        return acumuladoRepository.save(acumulado);
    }

    public void deletarAcumulado(Long id) {
        acumuladoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Acumulado com ID " + id + " não encontrado."));
        acumuladoRepository.deleteById(id);
    }

}