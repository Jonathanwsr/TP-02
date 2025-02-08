package TopReports.Service;



import TopReports.Dto.AcumuladoDTO;
import TopReports.Enity.Acumulado;
import TopReports.Exeption.ResourceNotFoundException;
import TopReports.Repository.AcumuladoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AcumuladoService {

    @Autowired
    private AcumuladoRepository acumuladoRepository;


    public List<AcumuladoDTO> buscarVendasPorPeriodo(LocalDate inicio, LocalDate fim) {
        List<Acumulado> acumulados = acumuladoRepository.findByDataBetween(inicio, fim);
        return acumulados.stream().map(this::toDTO).collect(Collectors.toList());
    }


    public BigDecimal calcularTotalValor(List<AcumuladoDTO> acumulados) {
        return acumulados.stream()
                .map(AcumuladoDTO::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    public Acumulado fromDTO(AcumuladoDTO dto) {
        Acumulado acumulado = new Acumulado();
        acumulado.setId(dto.getId());
        acumulado.setVenda(dto.getVenda());
        acumulado.setTipo(dto.getTipo());
        acumulado.setValor(dto.getValor());
        acumulado.setData(dto.getData());
        return acumulado;
    }


    public AcumuladoDTO toDTO(Acumulado acumulado) {
        return new AcumuladoDTO(
                acumulado.getId(),
                acumulado.getVenda(),
                acumulado.getTipo(),
                acumulado.getValor(),
                acumulado.getData()
        );
    }


    public Acumulado salvarAcumulado(Acumulado acumulado) {
        return acumuladoRepository.save(acumulado);
    }


    public Acumulado atualizarParcial(Long id, AcumuladoDTO parcial) throws ResourceNotFoundException {
        Acumulado acumuladoExistente = acumuladoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Acumulado não encontrado com ID: " + id));

        if (parcial.getVenda() != null) acumuladoExistente.setVenda(parcial.getVenda());
        if (parcial.getTipo() != null) acumuladoExistente.setTipo(parcial.getTipo());
        if (parcial.getValor() != null) acumuladoExistente.setValor(parcial.getValor());
        if (parcial.getData() != null) acumuladoExistente.setData(parcial.getData());

        return acumuladoRepository.save(acumuladoExistente);
    }

// COLOCAR UM METODO QUE CALACULA OS VALORES POR DATA DE INICIO E E FIM

    public void deletarAcumulado(Long id) throws ResourceNotFoundException {
        if (!acumuladoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Acumulado não encontrado com ID: " + id);
        }
        acumuladoRepository.deleteById(id);
    }
}

